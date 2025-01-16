package ru.ifmo.se.dating.people.storage

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Topic

interface TopicStorage {
    fun selectAll(): Flow<Topic>
}
