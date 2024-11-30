package ru.ifmo.se.dating.people.security

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.stereotype.Component
import ru.ifmo.se.dating.spring.security.Path
import ru.ifmo.se.dating.spring.security.SpringSecuredPaths

@Component
class PeopleSecuredPaths : SpringSecuredPaths {
    override val matcher: ServerWebExchangeMatcher = Path("")
}
