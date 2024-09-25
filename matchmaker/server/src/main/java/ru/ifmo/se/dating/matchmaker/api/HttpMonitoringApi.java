package ru.ifmo.se.dating.matchmaker.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
class HttpMonitoringApi implements MonitoringApiDelegate {

    @Override
    public ResponseEntity<String> monitoringHealthcheckGet() {
        return ResponseEntityStub.create();
    }
}
