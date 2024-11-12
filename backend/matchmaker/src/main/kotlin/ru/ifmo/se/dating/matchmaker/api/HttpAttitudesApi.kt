package ru.ifmo.se.dating.matchmaker.api

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.api.generated.AttitudesApiDelegate

@Controller
internal class HttpAttitudesApi : AttitudesApiDelegate {
    override suspend fun attitudesDelete(sourceId: Long): ResponseEntity<Unit> =
        ResponseEntityStub.create()
}
