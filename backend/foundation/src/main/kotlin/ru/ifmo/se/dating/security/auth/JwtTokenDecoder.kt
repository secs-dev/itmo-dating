package ru.ifmo.se.dating.security.auth

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import ru.ifmo.se.dating.exception.AuthenticationException
import java.security.PublicKey
import java.time.Clock
import java.util.*

class JwtTokenDecoder(
    private val clock: Clock,
    private val publicSignKey: PublicKey,
) : TokenDecoder {
    override fun decode(token: AccessToken): AccessToken.Payload =
        try {
            Jwts.parser()
                .verifyWith(publicSignKey)
                .clock { Date.from(clock.instant()) }
                .build()
                .parseSignedClaims(token.text)
                .payload
                .let { Jwt.deserialize(it) }
        } catch (e: ExpiredJwtException) {
            throw AuthenticationException(e.message!!, e)
        } catch (e: MalformedJwtException) {
            throw AuthenticationException(e.message!!, e)
        }
}
