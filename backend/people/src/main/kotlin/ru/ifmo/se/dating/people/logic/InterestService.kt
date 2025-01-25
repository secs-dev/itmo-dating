package ru.ifmo.se.dating.people.logic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.security.auth.User

interface InterestService {
    suspend fun insert(id: User.Id, interest: Person.Interest)
    suspend fun remove(id: User.Id, topicId: Topic.Id)
    suspend fun getAllTopics(): Flow<Topic>
}
