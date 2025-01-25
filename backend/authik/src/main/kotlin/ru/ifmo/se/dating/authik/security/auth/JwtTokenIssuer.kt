package ru.ifmo.se.dating.authik.security.auth

import io.jsonwebtoken.Jwts
import ru.ifmo.se.dating.security.auth.AccessToken
import ru.ifmo.se.dating.security.auth.Jwt
import java.security.PrivateKey
import java.time.Clock
import java.util.*
import kotlin.time.toJavaDuration
import java.time.Duration as JavaDuration
import kotlin.time.Duration as KotlinDuration

class JwtTokenIssuer(
    private val clock: Clock,
    private val privateSignKey: PrivateKey,
    duration: KotlinDuration,
) : TokenIssuer {
    private val duration: JavaDuration = duration.toJavaDuration()

    override suspend fun issue(payload: AccessToken.Payload): AccessToken {
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
