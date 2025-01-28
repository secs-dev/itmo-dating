package ru.ifmo.se.dating.matchmaker.soap.server.rest

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

object SpringHeaders {
    private const val bearerPrefix = "Bearer "

    fun getAuthorization(): String =
        getOnly("Authorization")
            .also { require(it.startsWith(bearerPrefix)) }
            .drop(bearerPrefix.length)

    private fun getOnly(name: String): String =
        RequestContextHolder
            .getRequestAttributes()
            .let { it as ServletRequestAttributes }
            .request
            .getHeader(name)
}
