package ru.ifmo.se.dating.matchmaker.security

import org.springframework.http.HttpMethod
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.stereotype.Component
import ru.ifmo.se.dating.spring.security.auth.And
import ru.ifmo.se.dating.spring.security.auth.Not
import ru.ifmo.se.dating.spring.security.auth.Path
import ru.ifmo.se.dating.spring.security.auth.SpringSecuredPaths

@Component
class MatchmakerSecuredPaths : SpringSecuredPaths {
    override val matcher: ServerWebExchangeMatcher = And(
        Path("/api/**"),
        Not(Path("/api/people/{person_id}", HttpMethod.PUT)),
        Not(Path("/api/monitoring/healthcheck", HttpMethod.GET)),
        Not(Path("/api/suggestions", HttpMethod.OPTIONS)),
    )
}
