package ru.ifmo.se.dating.matchmaker.soap.server.rest

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.server.api.generated.StatisticsApiDelegate
import ru.ifmo.se.dating.matchmaker.soap.client.soap.MatchmakerSoapClient

@Controller
class StatisticsEndpoint(private val client: MatchmakerSoapClient) : StatisticsApiDelegate {
    override fun statisticsAttitudesGet(): ResponseEntity<List<StatisticsEntry>> = client
        .getAttitudesStatistics(Unit)
        .statistics
        .map { it.toRest() }
        .let { ResponseEntity.ok(it) }
}
