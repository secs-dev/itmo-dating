package ru.ifmo.se.dating.logging

import io.github.numichi.reactive.logger.coroutine.CoroutineLogger

class Slf4jLog(name: String) : Log {
    private val origin = CoroutineLogger.getLogger(name)

    override suspend fun info(message: String) =
        origin.info(message)

    override suspend fun warn(message: String) =
        origin.warn(message)

    override suspend fun warn(message: String, e: Throwable) =
        origin.warn("$message: ${e.message}", e)

    override suspend fun error(message: String, e: Throwable) =
        origin.error("$message: ${e.message}", e)

    override suspend fun debug(message: String) =
        origin.debug(message)
}
