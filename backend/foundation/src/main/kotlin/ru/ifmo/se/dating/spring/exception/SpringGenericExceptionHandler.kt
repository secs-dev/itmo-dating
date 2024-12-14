package ru.ifmo.se.dating.spring.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.ifmo.se.dating.api.GeneralErrorMessage
import ru.ifmo.se.dating.exception.*

@RestControllerAdvice
class SpringGenericExceptionHandler(private val mapping: SpringExceptionMapping) {
    val GenericException.httpCode: HttpStatus
        get() = mapping.httpCode(this) ?: when (this) {
            is AuthenticationException -> HttpStatus.UNAUTHORIZED
            is AuthorizationException -> HttpStatus.UNAUTHORIZED
            is ConflictException -> HttpStatus.CONFLICT
            is InvalidValueException -> HttpStatus.BAD_REQUEST
            is NotFoundException -> HttpStatus.NOT_FOUND
            else -> throw NotImplementedError("$this")
        }

    @ExceptionHandler(GenericException::class)
    fun handle(exception: GenericException): ResponseEntity<GeneralErrorMessage> =
        toResponseEntity(exception)

    fun toResponseEntity(exception: GenericException): ResponseEntity<GeneralErrorMessage> =
        ResponseEntity
            .status(exception.httpCode)
            .body(
                GeneralErrorMessage(
                    code = exception.httpCode.value(),
                    status = exception.httpCode.reasonPhrase,
                    message = exception.message!!,
                ),
            )
}
