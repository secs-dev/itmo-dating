package ru.ifmo.se.dating.matchmaker.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.api.generated.StatisticsApiDelegate
import ru.ifmo.se.dating.matchmaker.api.mapping.toMessage
import ru.ifmo.se.dating.matchmaker.logic.StatisticsService
import ru.ifmo.se.dating.matchmaker.model.generated.StatisticsAttitudesGet200ResponseInnerMessage

typealias StatisticsAttitudesResponse =
    ResponseEntity<Flow<StatisticsAttitudesGet200ResponseInnerMessage>>

@Controller
class HttpStatisticsApi(private val service: StatisticsService) : StatisticsApiDelegate {
    override fun statisticsAttitudesGet(): StatisticsAttitudesResponse = runBlocking {
        service.selectAttitudesByPerson()
            .map { it.toMessage() }
            .let { ResponseEntity.ok(it) }
    }
}
