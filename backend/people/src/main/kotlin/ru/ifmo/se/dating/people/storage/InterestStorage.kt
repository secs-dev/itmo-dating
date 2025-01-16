package ru.ifmo.se.dating.people.storage

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.security.auth.User

interface InterestStorage {
    suspend fun upsert(id: User.Id, interest: Person.Interest)
    suspend fun delete(id: User.Id, topicId: Topic.Id)
    fun selectInterestsByPersonId(id: User.Id): Flow<Person.Interest>
    fun selectAllTopics(): Flow<Topic>
}
