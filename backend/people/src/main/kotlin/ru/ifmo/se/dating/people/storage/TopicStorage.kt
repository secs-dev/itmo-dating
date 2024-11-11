package ru.ifmo.se.dating.people.storage

import ru.ifmo.se.dating.people.model.Topic

interface TopicStorage {
    suspend fun getByIds(ids: Set<Topic.Id>): List<Topic>
}
