package ru.ifmo.se.dating.people.storage.jooq

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import org.jooq.generated.tables.records.PersonRecord
import org.jooq.generated.tables.references.PERSON
import org.jooq.generated.tables.references.PERSON_INTEREST
import org.jooq.generated.tables.references.PICTURE
import org.jooq.impl.DSL
import org.jooq.impl.DSL.currentOffsetDateTime
import org.jooq.impl.DSL.not
import org.jooq.kotlin.ge
import org.jooq.kotlin.le
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.exception.NotFoundException
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.pagging.Page
import ru.ifmo.se.dating.pagging.SortingKey
import ru.ifmo.se.dating.people.logic.PersonField
import ru.ifmo.se.dating.people.logic.PersonFilter
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.storage.InterestStorage
import ru.ifmo.se.dating.people.storage.LocationStorage
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.people.storage.PictureRecordStorage
import ru.ifmo.se.dating.people.storage.jooq.mapping.isReady
import ru.ifmo.se.dating.people.storage.jooq.mapping.toModel
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.FetchPolicy
import ru.ifmo.se.dating.storage.TxEnv
import ru.ifmo.se.dating.storage.jooq.JooqDatabase
import ru.ifmo.se.dating.storage.jooq.withPolicy
import java.time.LocalDate
import java.time.OffsetDateTime

@Suppress("TooManyFunctions")
@Repository
class JooqPersonStorage(
    private val database: JooqDatabase,
    private val txEnv: TxEnv,
    private val pictureRecords: PictureRecordStorage,
    private val interests: InterestStorage,
    private val locations: LocationStorage,
) : PersonStorage {
    private val log = autoLog()

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

    @Suppress("LongMethod", "CyclomaticComplexMethod", "CognitiveComplexMethod")
    override fun selectFilteredReady(
        page: Page,
        filter: PersonFilter,
        sortedBy: List<SortingKey<PersonField>>,
    ): Flow<Person> =
        database.flow {
            var cond = PERSON.READY_MOMENT.isNotNull

            filter.firstName?.let { cond = cond.and(PERSON.FIRST_NAME.likeRegex(it.pattern)) }

            filter.lastName?.let { cond = cond.and(PERSON.LAST_NAME.likeRegex(it.pattern)) }

            if (filter.height.first != Int.MIN_VALUE) {
                cond = cond.and(PERSON.HEIGHT.ge(filter.height.first))
            }
            if (filter.height.last != Int.MAX_VALUE) {
                cond = cond.and(PERSON.HEIGHT.le(filter.height.last))
            }

            if (filter.birthday.start != LocalDate.MIN) {
                cond = cond.and(PERSON.BIRTHDAY.ge(filter.birthday.start))
            }
            if (filter.birthday.endInclusive != LocalDate.MAX) {
                cond = cond.and(PERSON.BIRTHDAY.le(filter.birthday.endInclusive))
            }

            filter.facultyId?.let { cond = cond.and(PERSON.FACULTY_ID.eq(it.number)) }

            if (filter.updated.start != OffsetDateTime.MIN) {
                cond = cond.and(PERSON.UPDATE_MOMENT.ge(filter.updated.start))
            }
            if (filter.updated.endInclusive != OffsetDateTime.MAX) {
                cond = cond.and(PERSON.UPDATE_MOMENT.le(filter.updated.endInclusive))
            }

            val picturesCountQuery =
                selectCount()
                    .from(PICTURE)
                    .where(
                        PICTURE.IS_REFERENCED.eq(true)
                            .and(PICTURE.OWNER_ID.eq(PERSON.ACCOUNT_ID))
                    )

            if (filter.picturesCount.start != Int.MIN_VALUE) {
                cond = cond.and(picturesCountQuery.ge(filter.picturesCount.start))
            }
            if (filter.picturesCount.endInclusive != Int.MAX_VALUE) {
                cond = cond.and(picturesCountQuery.le(filter.picturesCount.endInclusive))
            }

            val interestIds =
                select(PERSON_INTEREST.TOPIC_ID)
                    .from(PERSON_INTEREST)
                    .where(PERSON_INTEREST.PERSON_ID.eq(PERSON.ACCOUNT_ID))

            for (topicId in filter.topicIds) {
                cond = cond.and(DSL.value(topicId.number).`in`(interestIds))
            }

            val orderByClause = sortedBy.map { sortingKey ->
                val field = when (sortingKey.key) {
                    PersonField.FIRST_NAME -> PERSON.FIRST_NAME
                    PersonField.LAST_NAME -> PERSON.LAST_NAME
                    PersonField.HEIGHT -> PERSON.HEIGHT
                    PersonField.BIRTHDAY -> PERSON.BIRTHDAY
                    PersonField.UPDATED -> PERSON.UPDATE_MOMENT
                }

                if (sortingKey.isReversed) {
                    field.desc()
                } else {
                    field.asc()
                }
            }

            selectFrom(PERSON)
                .where(cond)
                .orderBy(orderByClause)
                .offset(page.offset)
                .limit(page.limit)
                .also { log.warn("Executed SQL: ${it.sql}") }
                .also { log.warn("BindValues: ${it.bindValues}") }
        }
            .map { it.enrichToModel() as Person }
            .filter { filter.area == null || filter.area.contains(it.location.coordinates) }
            .filter { filter.zodiac == null || filter.zodiac == it.zodiac }

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
