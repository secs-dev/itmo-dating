package ru.ifmo.se.dating.people.storage.jooq

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import org.jooq.generated.tables.records.PersonRecord
import org.jooq.generated.tables.references.PERSON
import org.jooq.impl.DSL.currentOffsetDateTime
import org.jooq.impl.DSL.not
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.exception.NotFoundException
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.storage.InterestStorage
import ru.ifmo.se.dating.people.storage.LocationStorage
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.people.storage.PictureRecordStorage
import ru.ifmo.se.dating.people.storage.jooq.mapping.isReady
import ru.ifmo.se.dating.people.api.mapping.toModel
import ru.ifmo.se.dating.people.storage.jooq.mapping.toModel
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.FetchPolicy
import ru.ifmo.se.dating.storage.TxEnv
import ru.ifmo.se.dating.storage.jooq.JooqDatabase
import ru.ifmo.se.dating.storage.jooq.withPolicy

@Suppress("TooManyFunctions")
@Repository
class JooqPersonStorage(
    private val database: JooqDatabase,
    private val txEnv: TxEnv,
    private val pictureRecords: PictureRecordStorage,
    private val interests: InterestStorage,
    private val locations: LocationStorage,
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
    }.enrichToModel()

    override suspend fun setReadyMoment(id: User.Id) {
        database.maybe {
            update(PERSON)
                .set(PERSON.READY_MOMENT, currentOffsetDateTime())
                .set(PERSON.VERSION, PERSON.VERSION.plus(1))
                .where(PERSON.ACCOUNT_ID.eq(id.number))
        } ?: throw NotFoundException("user with id $id")
    }

    override suspend fun resetReadyMoment(id: User.Id) {
        database.maybe {
            update(PERSON)
                .setNull(PERSON.READY_MOMENT)
                .set(PERSON.VERSION, PERSON.VERSION.plus(1))
                .where(PERSON.ACCOUNT_ID.eq(id.number))
                .returning()
        } ?: throw NotFoundException("user with id $id")
    }

    override suspend fun selectById(id: User.Id, policy: FetchPolicy): PersonVariant? =
        database.maybe {
            selectFrom(PERSON)
                .where(PERSON.ACCOUNT_ID.eq(id.number))
                .withPolicy(policy)
        }?.enrichToModel()

    override fun selectNotSentIds(limit: Int): Flow<User.Id> = database.flow {
        select(PERSON.ACCOUNT_ID)
            .from(PERSON)
            .where(PERSON.READY_MOMENT.isNotNull.and(not(PERSON.IS_PUBLISHED)))
    }.map { User.Id(it[PERSON.ACCOUNT_ID.name]!! as Int) }

    override suspend fun setIsPublished(id: User.Id, isPublished: Boolean) = txEnv.transactional {
        database.only {
            update(PERSON)
                .set(PERSON.IS_PUBLISHED, isPublished)
                .where(PERSON.ACCOUNT_ID.eq(id.number))
        }
    }.let { }

    override fun selectAllReady(): Flow<Person> = database.flow {
        selectFrom(PERSON)
            .where(PERSON.READY_MOMENT.isNotNull)
    }.map { it.enrichToModel() as Person }

    private suspend fun PersonRecord.enrichToModel(): PersonVariant = coroutineScope {
        val userId = User.Id(accountId)

        val interests = async {
            interests
                .selectInterestsByPersonId(userId)
                .toSet(mutableSetOf())
        }

        val pictureIds = async {
            pictureRecords
                .selectByOwner(userId)
                .toList(mutableListOf())
                .map { it.id }
        }

        val location = if (isReady) {
            async { locations.selectById(Location.Id(locationId!!.toInt())) }
        } else {
            null
        }

        toModel(
            location = location?.await(),
            interests = interests.await(),
            pictureIds = pictureIds.await(),
        )
    }
}
