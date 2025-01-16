package ru.ifmo.se.dating.people.logic.logging

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.people.logic.TopicService
import ru.ifmo.se.dating.people.model.Topic

class LoggingTopicService(private val origin: TopicService) : TopicService {
    private val log = autoLog()

    override fun getAll(): Flow<Topic> =
        runCatching { origin.getAll() }
            .onSuccess { log.debug("Got all topics") }
            .onFailure { e -> log.warn("Failed to get all topics: ${e.message}") }
            .getOrThrow()
}
