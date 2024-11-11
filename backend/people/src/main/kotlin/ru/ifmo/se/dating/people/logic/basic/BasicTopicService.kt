package ru.ifmo.se.dating.people.logic.basic

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.people.logic.TopicService
import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.people.storage.TopicStorage

@Service
class BasicTopicService(private val storage: TopicStorage) : TopicService {
    override suspend fun getByIds(ids: Set<Topic.Id>): List<Topic> =
        storage.getByIds(ids)
}
