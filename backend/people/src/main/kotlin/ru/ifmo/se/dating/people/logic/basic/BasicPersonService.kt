package ru.ifmo.se.dating.people.logic.basic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import ru.ifmo.se.dating.exception.ConflictException
import ru.ifmo.se.dating.exception.InvalidValueException
import ru.ifmo.se.dating.exception.orThrowNotFound
import ru.ifmo.se.dating.pagging.Page
import ru.ifmo.se.dating.people.exception.IncompletePersonException
import ru.ifmo.se.dating.people.logic.PersonOutbox
import ru.ifmo.se.dating.people.logic.PersonService
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.TxEnv
import ru.ifmo.se.dating.storage.exception.LinkViolationException

class BasicPersonService(
    private val storage: PersonStorage,
    private val txEnv: TxEnv,
    private val outbox: PersonOutbox,
) : PersonService {
    private val background = CoroutineScope(Dispatchers.Default)

    override suspend fun edit(draft: Person.Draft) {
        try {
            val person = storage.upsert(draft)
            if (person is Person) {
                background.launch { outbox.process(person.id) }
            }
        } catch (exception: LinkViolationException) {
            throw InvalidValueException("faculty or location id does not exist", exception)
        }
    }

    override suspend fun save(expected: Person.Draft) = txEnv.transactional {
        val variant = getById(expected.id)
            .orThrowNotFound("person with id ${expected.id}")

        if (variant is Person) {
            throw ConflictException("person already saved")
        }

        if (
            variant is Person.Draft && expected.copy(
                pictureIds = variant.pictureIds,
                version = variant.version,
            ) != variant
        ) {
            throw ConflictException("provided person does not match an existing")
        }

        validate(expected)
        storage.setReadyMoment(expected.id)

        expected.id
    }.let { id -> background.launch { outbox.process(id) } }.let { }

    override suspend fun getById(id: User.Id): PersonVariant? =
        storage.selectById(id)

    override suspend fun delete(id: User.Id) = txEnv.transactional {
        storage.resetReadyMoment(id)
        storage.setIsPublished(id, false)
    }.let { background.launch { outbox.process(id) } }.let { }

    override fun getFiltered(page: Page, filter: PersonService.Filter): Flow<Person> =
        storage.selectAllReady()
            .drop(page.offset)
            .take(page.limit)
            .filter { filter.firstName.matches(it.firstName.text) }
            .filter { filter.lastName.matches(it.lastName.text) }
            .filter { filter.height.contains(it.height) }
            .filter { filter.birthday.contains(it.birthday) }
            .filter { filter.faculty.isEmpty() || filter.faculty.contains(it.facultyId) }

    @Suppress("ThrowsCount")
    private fun validate(draft: Person.Draft) {
        val id = draft.id
        draft.firstName ?: throw IncompletePersonException(id, "firstName")
        draft.lastName ?: throw IncompletePersonException(id, "lastName")
        draft.height ?: throw IncompletePersonException(id, "height")
        draft.birthday ?: throw IncompletePersonException(id, "birthday")
        draft.facultyId ?: throw IncompletePersonException(id, "facultyId")
        draft.locationId ?: throw IncompletePersonException(id, "locationId")
    }
}
