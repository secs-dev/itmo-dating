package ru.ifmo.se.dating.people.storage.jooq

import kotlinx.coroutines.flow.toCollection
import org.jooq.generated.tables.records.TopicRecord
import org.jooq.generated.tables.references.TOPIC
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.people.storage.TopicStorage
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqTopicStorage(private val database: JooqDatabase) : TopicStorage {
    override suspend fun getByIds(ids: Set<Topic.Id>): List<Topic> = database.flow {
        selectFrom(TOPIC).where(TOPIC.ID.`in`(ids.map { it.number }))
    }.toCollection(mutableListOf()).map { it.toModel() }

    private fun TopicRecord.toModel() = Topic(
        id = Topic.Id(id!!),
        name = name,
    )
}
