package ru.ifmo.se.dating.people.logic.basic

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import ru.ifmo.se.dating.exception.orThrowNotFound
import ru.ifmo.se.dating.people.exception.ConflictException
import ru.ifmo.se.dating.people.exception.IncompletePersonException
import ru.ifmo.se.dating.people.logic.PersonService
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.security.auth.User

@Service
class BasicPersonService(private val storage: PersonStorage) : PersonService {
    override suspend fun edit(draft: Person.Draft) =
        storage.upsert(draft)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override suspend fun save(expected: Person.Draft) {
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
}
