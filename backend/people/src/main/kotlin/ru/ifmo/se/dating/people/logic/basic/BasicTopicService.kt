package ru.ifmo.se.dating.people.logic.basic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.logic.TopicService
import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.people.storage.TopicStorage

class BasicTopicService(
    private val storage: TopicStorage,
) : TopicService {
    override fun getAll(): Flow<Topic> =
        storage.selectAll()
}
