package ru.ifmo.se.dating.matchmaker.api

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.api.generated.StatisticsApiDelegate
import ru.ifmo.se.dating.matchmaker.model.generated.StatisticsAttitudesGet200ResponseInner

typealias StatisticsAttitudesResponse =
    ResponseEntity<List<StatisticsAttitudesGet200ResponseInner>>

@Service
internal class HttpStatisticsApi : StatisticsApiDelegate {
    override fun statisticsAttitudesGet(): StatisticsAttitudesResponse =
        ResponseEntityStub.create()
}
