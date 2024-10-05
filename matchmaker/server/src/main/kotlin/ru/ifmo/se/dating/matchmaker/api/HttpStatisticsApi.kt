package ru.ifmo.se.dating.matchmaker.api

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.api.generated.StatisticsApiDelegate
import ru.ifmo.se.dating.matchmaker.model.generated.StatisticsAttitudesGet200ResponseInner

@Service
internal class HttpStatisticsApi : StatisticsApiDelegate {
    override fun statisticsAttitudesGet(
    ): ResponseEntity<Set<StatisticsAttitudesGet200ResponseInner>> =
        ResponseEntityStub.create()
}
