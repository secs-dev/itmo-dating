package ru.ifmo.se.dating.spring.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebInputException
import ru.ifmo.se.dating.api.GeneralErrorMessage

@RestControllerAdvice
class SpringDriverExceptionMapping {
    @ExceptionHandler(ServerWebInputException::class)
    fun handle(exception: ServerWebInputException) = run {
        val code = HttpStatus.BAD_REQUEST
        ResponseEntity
            .status(code)
            .body(
                GeneralErrorMessage(
                    code = code.value(),
                    status = code.reasonPhrase,
                    message = "Input is invalid: ${exception.reason ?: "?"}",
                ),
            )
    }
}
