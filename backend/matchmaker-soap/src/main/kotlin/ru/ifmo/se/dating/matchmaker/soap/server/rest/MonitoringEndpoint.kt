package ru.ifmo.se.dating.matchmaker.soap.server.rest

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.server.api.generated.MonitoringApiDelegate

@Controller
class MonitoringEndpoint : MonitoringApiDelegate {
    override fun monitoringHealthcheckGet(): ResponseEntity<String> =
        ResponseEntity.ok("pong")
}
