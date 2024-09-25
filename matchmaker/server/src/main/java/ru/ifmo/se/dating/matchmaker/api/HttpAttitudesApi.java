package ru.ifmo.se.dating.matchmaker.api;

import ru.ifmo.se.dating.matchmaker.api.generated.AttitudesApiDelegate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
class HttpAttitudesApi implements AttitudesApiDelegate {

    @Override
    public ResponseEntity<Void> attitudesDelete(Long sourceId) {
        return ResponseEntityStub.create();
    }
}
