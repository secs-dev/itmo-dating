package ru.ifmo.se.dating.matchmaker.soap.server.rest

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.ResetAttitudesRequest
import ru.ifmo.se.dating.matchmaker.server.api.generated.AttitudesApiDelegate
import ru.ifmo.se.dating.matchmaker.soap.client.soap.MatchmakerSoapClient

@Controller
class AttitudesEndpoint(private val client: MatchmakerSoapClient) : AttitudesApiDelegate {
    override fun attitudesDelete(sourceId: Long): ResponseEntity<Unit> = client
        .resetAttitudes(
            SpringHeaders.getAuthorization(),
            ResetAttitudesRequest().also {
                it.sourceId = sourceId.toInt()
            },
        )
        .let { ResponseEntity.ok(Unit) }
}
