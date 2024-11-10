package ru.ifmo.se.dating.matchmaker.api

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.ResponseEntity
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.api.generated.MonitoringApiDelegate

@Service
internal class HttpMonitoringApi(
    private val data: DatabaseClient,
) : MonitoringApiDelegate {
    override suspend fun monitoringHealthcheckGet(): ResponseEntity<String> =
        data
            .sql("SELECT current_schema")
            .map { row, _ -> row.get(0, String::class.java) }
            .one()
            .map { pong -> ResponseEntity.ok(pong) }
            .awaitSingle()
}
