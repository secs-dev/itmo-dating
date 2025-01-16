package ru.ifmo.se.dating.people.logic.logging

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.people.logic.InterestService
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.security.auth.User

class LoggingInterestService(private val origin: InterestService) : InterestService by origin {
    private val log = autoLog()

    override suspend fun insert(id: User.Id, interest: Person.Interest) =
        runCatching { origin.insert(id, interest) }
            .onSuccess {
                buildString {
                    append("Person with id $id is interested in ")
                    append("${interest.topicId} at degree ${interest.degree}")
                }.let { log.info(it) }
            }
            .getOrThrow()

    override suspend fun remove(id: User.Id, topicId: Topic.Id) =
        runCatching { origin.remove(id, topicId) }
            .onSuccess {
                log.info("Removed an interest in topic with id $topicId from user with id $id")
            }
            .onFailure { e ->
                buildString {
                    append("Failed to remove an interest in ")
                    append("topic with id $topicId from ")
                    append("user with id $id: ${e.message}")
                }.let { log.warn(it) }
            }
            .getOrThrow()

    override fun getAllTopics(): Flow<Topic> =
        runCatching { origin.getAllTopics() }
            .onSuccess { log.debug("Got all topics") }
            .onFailure { e -> log.warn("Failed to get all topics: ${e.message}") }
            .getOrThrow()
}
