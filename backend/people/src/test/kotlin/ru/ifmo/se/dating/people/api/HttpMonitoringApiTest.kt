package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.ifmo.se.dating.people.PeopleTestSuite

class HttpMonitoringApiTest : PeopleTestSuite() {
    @Test
    fun emptyTest() = Unit

    @Test
    fun healthcheck(): Unit = runBlocking {
        Assert.assertEquals(getHealthcheck(), "public")
    }

    private suspend fun getHealthcheck(): String = webClient
        .get()
        .uri("monitoring/healthcheck")
        .retrieve()
        .toEntity(String::class.java)
        .awaitSingle()
        .body!!
}
