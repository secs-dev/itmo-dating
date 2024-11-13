package ru.ifmo.se.dating.matchmaker.api

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.exception.DomainException
import ru.ifmo.se.dating.spring.SpringDomainExceptionMapping

@Controller
class HttpExceptionMapping : SpringDomainExceptionMapping<DomainException> {
    override val domainExceptionType: Class<DomainException>
        get() = DomainException::class.java

    override fun domainHttpCode(exception: DomainException): HttpStatus =
        TODO("There are no domain exceptions yet")
}
