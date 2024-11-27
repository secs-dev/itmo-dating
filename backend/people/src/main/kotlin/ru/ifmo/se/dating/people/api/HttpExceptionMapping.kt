package ru.ifmo.se.dating.people.api

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.people.exception.DomainException
import ru.ifmo.se.dating.people.exception.IncompletePersonException
import ru.ifmo.se.dating.spring.SpringDomainExceptionMapping

@Controller
class HttpExceptionMapping : SpringDomainExceptionMapping<DomainException> {
    override val domainExceptionType: Class<DomainException>
        get() = DomainException::class.java

    override fun domainHttpCode(exception: DomainException): HttpStatus =
        when (exception) {
            is IncompletePersonException -> TODO()
        }
}
