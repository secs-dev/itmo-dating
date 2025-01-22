package ru.ifmo.se.dating.security.auth

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.ifmo.se.dating.security.key.Keys
import java.time.Clock

@Configuration
class JwtTokenDecoderConfiguration {
    @Bean
    fun jwtTokenDecoder(
        clock: Clock,

        @Value("\${itmo-dating.auth.jwt.public-key}")
        publicSignKey: String,
    ) = LoggingTokenDecoder(
        JwtTokenDecoder(
            clock = clock,
            publicSignKey = Keys.deserializePublic(publicSignKey),
        )
    )
}
