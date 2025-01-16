package ru.ifmo.se.dating.authik.logic.logging

import ru.ifmo.se.dating.authik.logic.AuthService
import ru.ifmo.se.dating.authik.model.generated.TelegramWebAppInitDataMessage
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.security.auth.AccessToken

class LoggingAuthService(val origin: AuthService) : AuthService {
    private val log = autoLog()

    override suspend fun authenticate(telegram: TelegramWebAppInitDataMessage): AccessToken =
        runCatching { origin.authenticate(telegram) }
            .onSuccess { log.debug("Authenticated telegram user with id ${telegram.user.id}") }
            .onFailure { e ->
                buildString {
                    append("Failed to authenticate telegram ")
                    append("with id ${telegram.user.id}: ${e.message}")
                }.let { log.warn(it) }
            }
            .getOrThrow()
}
