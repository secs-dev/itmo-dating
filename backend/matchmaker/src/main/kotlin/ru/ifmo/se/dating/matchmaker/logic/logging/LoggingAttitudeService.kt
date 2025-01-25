package ru.ifmo.se.dating.matchmaker.logic.logging

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.matchmaker.logic.AttitudeService
import ru.ifmo.se.dating.matchmaker.model.Attitude
import ru.ifmo.se.dating.security.auth.User

class LoggingAttitudeService(private val origin: AttitudeService) : AttitudeService {
    private val log = autoLog()

    override suspend fun express(attitude: Attitude) =
        runCatching { origin.express(attitude) }
            .onSuccess {
                buildString {
                    append("Expressed ${attitude.kind}")
                    append(" from ${attitude.sourceId}")
                    append(" to ${attitude.targetId}")
                }.let { log.debug(it) }
            }
            .onFailure { e ->
                buildString {
                    append("Failed to express ${attitude.kind} ")
                    append("from ${attitude.sourceId} ")
                    append("to ${attitude.targetId}: ")
                    append("${e.message}")
                }.let { log.warn(it) }
            }
            .getOrThrow()

    override suspend fun matches(client: User.Id): Flow<User.Id> =
        runCatching { origin.matches(client) }
            .onSuccess { log.debug("Got matches for client with id ${client.number}") }
            .onFailure { e ->
                buildString {
                    append("Failed to get matches ")
                    append("for client with id ${client.number}: ")
                    append(e.message)
                }.let { log.warn(it) }
            }
            .getOrThrow()

    override suspend fun suggestions(client: User.Id, limit: Int): Flow<User.Id> =
        runCatching { origin.suggestions(client, limit) }
            .onSuccess {
                log.debug("Got no more than $limit suggestions for client with id $client")
            }
            .onFailure { e ->
                log.warn("Failed to retrieve suggestions for client with id $client: ${e.message}")
            }
            .getOrThrow()
}
