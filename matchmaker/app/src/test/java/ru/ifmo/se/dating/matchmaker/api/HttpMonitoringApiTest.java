package ru.ifmo.se.dating.matchmaker.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ru.ifmo.se.dating.matchmaker.MatchmakerTestSuite;

public final class HttpMonitoringApiTest extends MatchmakerTestSuite {
    @Autowired
    private TestRestTemplate rest;

    @Test
    public void healthcheck() {
        assertEquals(getHealthcheck(), "public");
    }

    private String getHealthcheck() {
        final var url = "http://localhost:8080/api/v1/monitoring/healthcheck";
        final var response = rest.getForEntity(url, String.class);
        return response.getBody();
    }
}
