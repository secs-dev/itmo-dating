package ru.ifmo.se.dating.spring

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.ifmo.se.dating.api.GeneralErrorMessage
import ru.ifmo.se.dating.exception.AuthenticationException
import ru.ifmo.se.dating.exception.GenericException
import ru.ifmo.se.dating.exception.InvalidValueException
import ru.ifmo.se.dating.exception.NotFoundException

interface SpringExceptionMapping {
    fun httpCode(exception: GenericException): HttpStatus?
}

interface SpringDomainExceptionMapping<T : GenericException> : SpringExceptionMapping {
    val domainExceptionType: Class<T>
    fun domainHttpCode(exception: T): HttpStatus

    @Suppress("UNCHECKED_CAST")
    override fun httpCode(exception: GenericException): HttpStatus? =
        if (domainExceptionType.isInstance(exception)) {
            domainHttpCode(exception as T)
        } else {
            null
        }
}

@RestControllerAdvice
class SpringGenericExceptionHandler(private val mapping: SpringExceptionMapping) {
    val GenericException.httpCode: HttpStatus
        get() = mapping.httpCode(this) ?: when (this) {
            is InvalidValueException -> HttpStatus.BAD_REQUEST
            is NotFoundException -> HttpStatus.NOT_FOUND
            is AuthenticationException -> HttpStatus.UNAUTHORIZED
            else -> throw NotImplementedError("$this")
        }

    @ExceptionHandler(GenericException::class)
    fun handle(exception: GenericException) =
        exception.toResponseEntity()

    fun GenericException.toResponseEntity() =
        ResponseEntity
            .status(this.httpCode)
            .body(
                GeneralErrorMessage(
                    code = this.httpCode.value(),
                    status = this.httpCode.reasonPhrase,
                    message = this.message!!,
                ),
            )
}
