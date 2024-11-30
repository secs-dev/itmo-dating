package ru.ifmo.se.dating.authik.security

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.stereotype.Component
import ru.ifmo.se.dating.spring.security.SpringSecuredPaths
import ru.ifmo.se.dating.spring.security.Path

@Component
class AuthikSecuredPaths : SpringSecuredPaths {
    override val matcher: ServerWebExchangeMatcher = Path("")
}
