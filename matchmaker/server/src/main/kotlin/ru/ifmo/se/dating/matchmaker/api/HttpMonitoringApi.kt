package ru.ifmo.se.dating.matchmaker.api

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.api.generated.MonitoringApiDelegate
import javax.sql.DataSource

@Service
internal class HttpMonitoringApi(
    private val data: DataSource,
) : MonitoringApiDelegate {
    override fun monitoringHealthcheckGet(): ResponseEntity<String> =
        data.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery("SELECT current_schema").use { result ->
                    check(result.next())
                    val pong = result.getString(1)
                    ResponseEntity.ok(pong)
                }
            }
        }
}
