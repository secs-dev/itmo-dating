package ru.ifmo.se.dating.people.storage.jooq

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jooq.generated.tables.records.PersonRecord
import org.jooq.generated.tables.references.PERSON
import org.jooq.impl.DSL.currentOffsetDateTime
import org.jooq.impl.DSL.not
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.FetchPolicy
import ru.ifmo.se.dating.storage.TxEnv
import ru.ifmo.se.dating.storage.jooq.JooqDatabase
import ru.ifmo.se.dating.storage.jooq.withPolicy

@Repository
class JooqPersonStorage(
    private val database: JooqDatabase,
    private val txEnv: TxEnv,
) : PersonStorage {
    @Suppress("CyclomaticComplexMethod")
    override suspend fun upsert(draft: Person.Draft): PersonVariant = txEnv.transactional {
        database.only {
            insertInto(PERSON)
                .set(PERSON.ACCOUNT_ID, draft.id.number)
                .set(PERSON.UPDATE_MOMENT, currentOffsetDateTime())
                .also { q -> draft.firstName?.text?.let { q.set(PERSON.FIRST_NAME, it) } }
                .also { q -> draft.lastName?.text?.let { q.set(PERSON.LAST_NAME, it) } }
                .also { q -> draft.height?.let { q.set(PERSON.HEIGHT, it) } }
                .also { q -> draft.birthday?.let { q.set(PERSON.BIRTHDAY, it) } }
                .also { q -> draft.facultyId?.number?.let { q.set(PERSON.FACULTY_ID, it) } }
                .also { q -> draft.locationId?.number?.let { q.set(PERSON.LOCATION_ID, it) } }
                .onConflict(PERSON.ACCOUNT_ID)
                .doUpdate()
                .set(PERSON.ACCOUNT_ID, draft.id.number)
                .set(PERSON.UPDATE_MOMENT, currentOffsetDateTime())
                .set(PERSON.VERSION, PERSON.VERSION.plus(1))
                .set(PERSON.IS_PUBLISHED, false)
                .also { q -> draft.firstName?.text?.let { q.set(PERSON.FIRST_NAME, it) } }
                .also { q -> draft.lastName?.text?.let { q.set(PERSON.LAST_NAME, it) } }
                .also { q -> draft.height?.let { q.set(PERSON.HEIGHT, it) } }
                .also { q -> draft.birthday?.let { q.set(PERSON.BIRTHDAY, it) } }
                .also { q -> draft.facultyId?.number?.let { q.set(PERSON.FACULTY_ID, it) } }
                .also { q -> draft.locationId?.number?.let { q.set(PERSON.LOCATION_ID, it) } }
                .returning()
        }
    }.toModel()

    override suspend fun setReadyMoment(id: User.Id) = database.only {
        update(PERSON)
            .set(PERSON.READY_MOMENT, currentOffsetDateTime())
            .where(PERSON.ACCOUNT_ID.eq(id.number))
    }.let { }

    override suspend fun selectById(id: User.Id, policy: FetchPolicy): PersonVariant? =
        database.maybe {
            selectFrom(PERSON)
                .where(PERSON.ACCOUNT_ID.eq(id.number))
                .withPolicy(policy)
        }?.toModel()

    override fun selectNotSentIds(limit: Int): Flow<User.Id> = database.flow {
        select(PERSON.ACCOUNT_ID)
            .from(PERSON)
            .where(PERSON.READY_MOMENT.isNotNull.and(not(PERSON.IS_PUBLISHED)))
    }.map { User.Id(it[PERSON.ACCOUNT_ID.name]!! as Int) }

    override suspend fun markSent(id: User.Id) = txEnv.transactional {
        database.only {
            update(PERSON)
                .set(PERSON.IS_PUBLISHED, true)
                .where(PERSON.ACCOUNT_ID.eq(id.number))
        }
    }.let { }

    override fun selectAllReady(): Flow<Person> = database.flow {
        selectFrom(PERSON)
            .where(PERSON.READY_MOMENT.isNotNull)
    }.map { it.toModel() as Person }

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
                version = Person.Version(version!!),
                isPublished = isPublished!!,
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
