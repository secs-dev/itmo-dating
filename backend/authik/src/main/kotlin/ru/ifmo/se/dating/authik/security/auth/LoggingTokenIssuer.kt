package ru.ifmo.se.dating.authik.security.auth

import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.security.auth.AccessToken

class LoggingTokenIssuer(private val origin: TokenIssuer) : TokenIssuer {
    private val log = autoLog()

    override fun issue(payload: AccessToken.Payload): AccessToken =
        runCatching { origin.issue(payload) }
            .onSuccess { log.info("Issued access token for user with id ${payload.userId}") }
            .onFailure { e ->
                log.warn("Failed to issue access token for user with id ${payload.userId}: ${e.message}")
            }
            .getOrThrow()
}
