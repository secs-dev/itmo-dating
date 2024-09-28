package ru.ifmo.se.dating.people.api;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.ToString;
import ru.ifmo.se.dating.people.api.generated.MonitoringApiDelegate;

@ToString
@Service
class HttpMonitoringApi implements MonitoringApiDelegate {
    private final DataSource data;

    HttpMonitoringApi(DataSource data) {
        this.data = data;
    }

    @Override
    public ResponseEntity<String> monitoringHealthcheckGet() {
        try (
            var connection = data.getConnection();
            var statement = connection.createStatement();
            var result = statement.executeQuery("SELECT current_schema");
        ) {
            if (!result.next()) {
                throw new IllegalStateException();
            }

            var pong = result.getString(1);
            return ResponseEntity.ok(pong);
        } catch (SQLException | IllegalStateException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
