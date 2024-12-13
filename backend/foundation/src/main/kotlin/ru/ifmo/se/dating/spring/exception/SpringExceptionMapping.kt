package ru.ifmo.se.dating.spring.exception

import org.springframework.http.HttpStatus
import ru.ifmo.se.dating.exception.GenericException

interface SpringExceptionMapping {
    fun httpCode(exception: GenericException): HttpStatus?
}
