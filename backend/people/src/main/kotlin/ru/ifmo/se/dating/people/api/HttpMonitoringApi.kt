package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.ResponseEntity
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.people.api.generated.MonitoringApiDelegate

@Controller
class HttpMonitoringApi(
    private val data: DatabaseClient,
) : MonitoringApiDelegate {
    override suspend fun monitoringHealthcheckGet(): ResponseEntity<String> {
        data
            .sql("SELECT current_schema")
            .map { row, _ -> row.get(0, String::class.java) }
            .one()
            .map { pong -> ResponseEntity.ok(pong) }
            .awaitSingle()
        return ResponseEntity.ok("pong")
    }
}
