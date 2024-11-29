package ru.ifmo.se.dating.people.logic.basic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.exception.ConflictException
import ru.ifmo.se.dating.exception.InvalidValueException
import ru.ifmo.se.dating.exception.orThrowNotFound
import ru.ifmo.se.dating.pagging.Page
import ru.ifmo.se.dating.people.exception.IncompletePersonException
import ru.ifmo.se.dating.people.logic.PersonService
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.TxEnv
import ru.ifmo.se.dating.storage.exception.LinkViolationException

@Service
class BasicPersonService(
    private val storage: PersonStorage,
    private val txEnv: TxEnv,
) : PersonService {
    override suspend fun edit(draft: Person.Draft) = try {
        storage.upsert(draft)
    } catch (exception: LinkViolationException) {
        throw InvalidValueException("faculty or location id does not exist", exception)
    }

    override suspend fun save(expected: Person.Draft) = txEnv.transactional {
        val variant = getById(expected.id)
            .orThrowNotFound("person with id ${expected.id}")

        if (variant is Person) {
            throw ConflictException("person already saved")
        }

        if (variant is Person.Draft && expected != variant) {
            throw ConflictException("provided person does not match an existing")
        }

        val id = expected.id
        expected.firstName ?: throw IncompletePersonException(id, "firstName")
        expected.lastName ?: throw IncompletePersonException(id, "lastName")
        expected.height ?: throw IncompletePersonException(id, "height")
        expected.birthday ?: throw IncompletePersonException(id, "birthday")
        expected.facultyId ?: throw IncompletePersonException(id, "facultyId")
        expected.locationId ?: throw IncompletePersonException(id, "locationId")

        storage.setReadyMoment(id)
    }

    override suspend fun getById(id: User.Id): PersonVariant? =
        storage.selectById(id)

    override fun getFiltered(page: Page, filter: PersonService.Filter): Flow<Person> =
        storage.selectAllReady()
            .drop(page.offset)
            .take(page.limit)
            .filter { filter.firstName.matches(it.firstName.text) }
            .filter { filter.lastName.matches(it.lastName.text) }
            .filter { filter.height.contains(it.height) }
            .filter { filter.birthday.contains(it.birthday) }
            .filter { filter.faculty.isEmpty() || filter.faculty.contains(it.facultyId) }
}
