package ru.ifmo.se.dating.matchmaker.api

import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.api.generated.StatisticsApiDelegate
import ru.ifmo.se.dating.matchmaker.model.generated.StatisticsAttitudesGet200ResponseInnerMessage

typealias StatisticsAttitudesResponse =
    ResponseEntity<Flow<StatisticsAttitudesGet200ResponseInnerMessage>>

@Service
internal class HttpStatisticsApi : StatisticsApiDelegate {
    override fun statisticsAttitudesGet(): StatisticsAttitudesResponse =
        ResponseEntityStub.create()
}