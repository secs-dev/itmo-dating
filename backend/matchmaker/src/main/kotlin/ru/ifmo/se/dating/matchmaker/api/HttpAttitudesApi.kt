package ru.ifmo.se.dating.matchmaker.api

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.api.generated.AttitudesApiDelegate

@Service
internal class HttpAttitudesApi : AttitudesApiDelegate {
    override fun attitudesDelete(sourceId: Long): ResponseEntity<Unit> =
        ResponseEntityStub.create()
}
