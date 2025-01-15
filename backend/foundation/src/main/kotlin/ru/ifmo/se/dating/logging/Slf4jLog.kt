package ru.ifmo.se.dating.logging

import org.slf4j.LoggerFactory

class Slf4jLog(name: String) : Log {
    private val origin = LoggerFactory.getLogger(name)

    override fun info(message: String) =
        origin.info(message)

    override fun warn(message: String) =
        origin.warn(message)

    override fun warn(message: String, e: Throwable) =
        origin.warn("${message}: ${e.message}", e)

    override fun error(message: String, e: Throwable) =
        origin.error("${message}: ${e.message}", e)

    override fun debug(message: String) =
        origin.debug(message)
}
