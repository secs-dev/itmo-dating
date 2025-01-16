package ru.ifmo.se.dating.people.logic.basic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.logic.InterestService
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.people.storage.InterestStorage
import ru.ifmo.se.dating.security.auth.User

class BasicInterestService(
    private val storage: InterestStorage,
) : InterestService {
    override suspend fun insert(id: User.Id, interest: Person.Interest) =
        storage.upsert(id, interest)

    override suspend fun remove(id: User.Id, topicId: Topic.Id) =
        storage.delete(id, topicId)

    override fun getAllTopics(): Flow<Topic> =
        storage.selectAllTopics()
}
