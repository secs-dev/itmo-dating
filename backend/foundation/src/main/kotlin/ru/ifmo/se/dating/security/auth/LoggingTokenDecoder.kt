package ru.ifmo.se.dating.security.auth

import ru.ifmo.se.dating.logging.Log.Companion.autoLog

class LoggingTokenDecoder(private val origin: TokenDecoder) : TokenDecoder {
    private val log = autoLog()

    override suspend fun decode(token: AccessToken): AccessToken.Payload =
        runCatching { origin.decode(token) }
            .onSuccess { log.debug("Decoded a token of user with id ${it.userId}") }
            .onFailure { log.warn("Failed to decode an auth token") }
            .getOrThrow()
}
