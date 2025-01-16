package ru.ifmo.se.dating.people.storage.jooq

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jooq.generated.tables.references.PERSON_INTEREST
import org.jooq.generated.tables.references.TOPIC
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.people.storage.InterestStorage
import ru.ifmo.se.dating.people.storage.jooq.mapping.toModel
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqInterestStorage(
    private val database: JooqDatabase,
) : InterestStorage {
    override suspend fun upsert(id: User.Id, interest: Person.Interest) = database.only {
        insertInto(PERSON_INTEREST)
            .set(PERSON_INTEREST.PERSON_ID, id.number)
            .set(PERSON_INTEREST.TOPIC_ID, interest.topicId.number)
            .set(PERSON_INTEREST.DEGREE, interest.degree)
            .onConflict(PERSON_INTEREST.PERSON_ID, PERSON_INTEREST.TOPIC_ID)
            .doUpdate()
            .set(PERSON_INTEREST.DEGREE, interest.degree)
            .returning()
    }.let { }

    override suspend fun delete(id: User.Id, topicId: Topic.Id) = database.only {
        delete(PERSON_INTEREST)
            .where(
                PERSON_INTEREST.PERSON_ID.eq(id.number)
                    .and(PERSON_INTEREST.TOPIC_ID.eq(topicId.number))
            )
    }.let {}

    override fun selectInterestsByPersonId(id: User.Id) = database.flow {
        selectFrom(PERSON_INTEREST)
            .where(PERSON_INTEREST.PERSON_ID.eq(id.number))
    }.map { it.toModel() }

    override fun selectAllTopics(): Flow<Topic> = database.flow {
        selectFrom(TOPIC)
    }.map { it.toModel() }
}
