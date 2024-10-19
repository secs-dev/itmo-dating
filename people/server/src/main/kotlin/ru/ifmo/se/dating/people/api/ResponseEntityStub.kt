package ru.ifmo.se.dating.people.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

internal object ResponseEntityStub {
    fun <T> create(): ResponseEntity<T> =
        ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build()
}
