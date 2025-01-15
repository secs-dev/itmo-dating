package ru.ifmo.se.dating.matchmaker.logic.logging

import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.matchmaker.logic.PersonService
import ru.ifmo.se.dating.matchmaker.model.PersonUpdate

class LoggingPersonService(private val origin: PersonService) : PersonService {
    private val log = autoLog()

    override suspend fun account(update: PersonUpdate) =
        runCatching { origin.account(update) }
            .onSuccess {
                buildString {
                    append("Accounted a person with id ${update.id} ")
                    append("with status ${update.status}, ")
                    append("version ${update.version}")
                }.let { log.info(it) }
            }
            .onFailure { e ->
                log.warn("Failed to account a person with id ${update.id} update: ${e.message}")
            }
            .getOrThrow()
}
