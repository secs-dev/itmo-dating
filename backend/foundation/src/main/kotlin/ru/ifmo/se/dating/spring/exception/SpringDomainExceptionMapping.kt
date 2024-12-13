package ru.ifmo.se.dating.spring.exception

import org.springframework.http.HttpStatus
import ru.ifmo.se.dating.exception.GenericException

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
