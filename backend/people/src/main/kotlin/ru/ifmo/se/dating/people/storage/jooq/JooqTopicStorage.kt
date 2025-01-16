package ru.ifmo.se.dating.people.storage.jooq

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jooq.generated.tables.references.TOPIC
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.people.storage.TopicStorage
import ru.ifmo.se.dating.people.storage.jooq.mapping.toModel
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqTopicStorage(
    private val database: JooqDatabase,
) : TopicStorage {
    override fun selectAll(): Flow<Topic> = database.flow {
        selectFrom(TOPIC)
    }.map { it.toModel() }
}
