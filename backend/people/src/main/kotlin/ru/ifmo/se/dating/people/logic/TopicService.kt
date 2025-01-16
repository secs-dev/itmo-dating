package ru.ifmo.se.dating.people.logic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Topic

interface TopicService {
    fun getAll(): Flow<Topic>
}
