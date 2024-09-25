package ru.ifmo.se.dating.matchmaker.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

final class ResponseEntityStub {
    private ResponseEntityStub() {

    }

    static <T> ResponseEntity<T> create() {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
