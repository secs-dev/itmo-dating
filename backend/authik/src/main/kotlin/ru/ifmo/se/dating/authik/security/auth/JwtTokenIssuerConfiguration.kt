package ru.ifmo.se.dating.authik.security.auth

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.ifmo.se.dating.security.key.Keys
import java.time.Clock
import java.time.Duration
import kotlin.time.toKotlinDuration

@Configuration
class JwtTokenIssuerConfiguration {
    @Bean
    fun jwtTokenIssuer(
        clock: Clock,

        @Value("\${security.auth.token.sign.private}")
        privateSignKey: String,

        @Value("\${security.auth.token.duration}")
        duration: Duration,
    ) = LoggingTokenIssuer(
        JwtTokenIssuer(
            clock = clock,
            privateSignKey = Keys.deserializePrivate(privateSignKey),
            duration = duration.toKotlinDuration(),
        )
    )
}
