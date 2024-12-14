package ru.ifmo.se.dating.authik.api

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.ifmo.se.dating.authik.AuthikTestSuite

class HttpMonitoringApiTest : AuthikTestSuite() {
    @Test
    fun emptyTest() = Unit

    @Test
    fun healthcheck(): Unit = runBlocking {
        Assert.assertEquals("pong", getHealthcheck())
    }

    private suspend fun getHealthcheck(): String = self
        .get()
        .uri("monitoring/healthcheck")
        .retrieve()
        .toEntity(String::class.java)
        .awaitSingle()
        .body!!
}
