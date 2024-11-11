package ru.ifmo.se.dating.people.logic

import ru.ifmo.se.dating.people.model.Topic

interface TopicService {
    suspend fun getByIds(ids: Set<Topic.Id>): List<Topic>
}
