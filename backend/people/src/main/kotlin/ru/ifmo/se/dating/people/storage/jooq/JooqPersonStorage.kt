package ru.ifmo.se.dating.people.storage.jooq

import org.jooq.generated.tables.records.PersonRecord
import org.jooq.generated.tables.references.PERSON
import org.jooq.impl.DSL.currentOffsetDateTime
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqPersonStorage(private val database: JooqDatabase) : PersonStorage {
    @Transactional(isolation = Isolation.SERIALIZABLE)
    override suspend fun upsert(draft: Person.Draft) {
        database.only {
            val record = draft.toRecord()
            insertInto(PERSON)
                .values(record)
                .onConflict(PERSON.ACCOUNT_ID)
                .doUpdate()
                .set(record)
        }

        database.only {
            update(PERSON)
                .set(PERSON.UPDATE_MOMENT, currentOffsetDateTime())
                .where(PERSON.ACCOUNT_ID.eq(draft.id.number))
        }
    }

    override suspend fun setReadyMoment(id: User.Id) = database.only {
        update(PERSON)
            .set(PERSON.READY_MOMENT, currentOffsetDateTime())
            .where(PERSON.ACCOUNT_ID.eq(id.number))
    }.let { }

    override suspend fun selectById(id: User.Id): PersonVariant? = database.maybe {
        selectFrom(PERSON)
            .where(PERSON.ACCOUNT_ID.eq(id.number))
    }?.toModel()

    fun Person.Draft.toRecord() = PersonRecord(
        accountId = id.number,
        firstName = firstName?.text,
        lastName = lastName?.text,
        height = height,
        birthday = birthday,
        facultyId = facultyId?.number,
        locationId = locationId?.number,
    )

    fun PersonRecord.toModel(): PersonVariant =
        if (readyMoment != null) {
            Person(
                id = User.Id(accountId),
                firstName = Person.Name(firstName!!),
                lastName = Person.Name(lastName!!),
                height = height!!,
                birthday = birthday!!,
                facultyId = Faculty.Id(facultyId!!),
                locationId = Location.Id(locationId!!),
            )
        } else {
            Person.Draft(
                id = User.Id(accountId),
                firstName = firstName?.let { Person.Name(it) },
                lastName = lastName?.let { Person.Name(it) },
                height = height,
                birthday = birthday,
                facultyId = facultyId?.let { Faculty.Id(it) },
                locationId = locationId?.let { Location.Id(it) },
            )
        }
}
