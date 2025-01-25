package ru.ifmo.se.dating.authik.security

import org.springframework.http.HttpMethod
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.stereotype.Component
import ru.ifmo.se.dating.spring.security.auth.And
import ru.ifmo.se.dating.spring.security.auth.Not
import ru.ifmo.se.dating.spring.security.auth.Path
import ru.ifmo.se.dating.spring.security.auth.SpringSecuredPaths

@Component
class AuthikSecuredPaths : SpringSecuredPaths {
    override val matcher: ServerWebExchangeMatcher = And(
        Path("/api/**"),
        Not(Path("/api/auth/telegram/web-app", HttpMethod.GET)),
        Not(Path("/api/monitoring/healthcheck", HttpMethod.PUT)),
        Not(Path("/actuator/prometheus", HttpMethod.GET)),
    )
}
