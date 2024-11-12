package ru.ifmo.se.dating.authik.security.auth

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.ifmo.se.dating.security.auth.AccessToken
import ru.ifmo.se.dating.security.auth.Jwt
import ru.ifmo.se.dating.security.key.Keys
import java.security.PrivateKey
import java.time.Clock
import java.util.*
import kotlin.time.toJavaDuration
import kotlin.time.toKotlinDuration
import java.time.Duration as JavaDuration
import kotlin.time.Duration as KotlinDuration

@Configuration
open class JwtTokenIssuerConfiguration {
    @Bean
    open fun jwtTokenIssuer(
        clock: Clock,

        @Value("\${security.auth.token.sign.private}")
        privateSignKey: String,

        @Value("\${security.auth.token.duration}")
        duration: JavaDuration,
    ) = JwtTokenIssuer(
        clock = clock,
        privateSignKey = Keys.deserializePrivate(privateSignKey),
        duration = duration.toKotlinDuration(),
    )
}

class JwtTokenIssuer(
    private val clock: Clock,
    private val privateSignKey: PrivateKey,
    duration: KotlinDuration,
) : TokenIssuer {
    private val duration: JavaDuration = duration.toJavaDuration()

    override fun issue(payload: AccessToken.Payload): AccessToken {
        val now = clock.instant()
        return Jwts.builder()
            .claims(Jwt.serialize(payload))
            .issuedAt(Date.from(now))
            .expiration(Date.from(now + duration))
            .signWith(privateSignKey)
            .compact()
            .let { AccessToken(it) }
    }
}
