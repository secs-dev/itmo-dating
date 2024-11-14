package ru.ifmo.se.dating.people.api

import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import ru.ifmo.se.dating.people.PeopleTestSuite

class HttpMonitoringApiTest : PeopleTestSuite() {
    @Autowired
    private lateinit var rest: TestRestTemplate

//    TODO write test with https
//    @Test
    fun healthcheck() {
        Assert.assertEquals(getHealthcheck(), "public")
    }

    private fun getHealthcheck(): String {
        val url = "http://localhost:8080/api/monitoring/healthcheck"
        val response = rest.getForEntity(url, String::class.java)
        return response.body!!
    }
}
