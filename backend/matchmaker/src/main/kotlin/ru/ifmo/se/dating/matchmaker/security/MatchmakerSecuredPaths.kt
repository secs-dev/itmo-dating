package ru.ifmo.se.dating.matchmaker.security

import org.springframework.http.HttpMethod
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.stereotype.Component
import ru.ifmo.se.dating.spring.security.And
import ru.ifmo.se.dating.spring.security.Not
import ru.ifmo.se.dating.spring.security.Path
import ru.ifmo.se.dating.spring.security.SpringSecuredPaths

@Component
class MatchmakerSecuredPaths : SpringSecuredPaths {
    override val matcher: ServerWebExchangeMatcher = And(
        Path("/api/**"),
        Not(Path("api/people/{person_id}", HttpMethod.PUT)),
        Not(Path("monitoring/healthcheck", HttpMethod.GET)),
    )
}
