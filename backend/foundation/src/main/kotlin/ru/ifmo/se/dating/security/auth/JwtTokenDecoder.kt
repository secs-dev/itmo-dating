package ru.ifmo.se.dating.security.auth

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.ifmo.se.dating.exception.AuthenticationException
import ru.ifmo.se.dating.security.key.Keys
import java.security.PublicKey
import java.time.Clock
import java.util.*

@Configuration
class JwtTokenDecoderConfiguration {
    @Bean
    fun jwtTokenDecoder(
        clock: Clock,

        @Value("\${security.auth.token.sign.public}")
        publicSignKey: String,
    ) = JwtTokenDecoder(
        clock = clock,
        publicSignKey = Keys.deserializePublic(publicSignKey),
    )
}

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
