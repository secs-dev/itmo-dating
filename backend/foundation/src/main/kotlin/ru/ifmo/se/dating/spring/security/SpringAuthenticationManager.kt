package ru.ifmo.se.dating.spring.security

import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ru.ifmo.se.dating.security.auth.AccessToken
import ru.ifmo.se.dating.security.auth.TokenDecoder
import org.springframework.security.core.Authentication as SpringAuthentication

@Component
class SpringAuthenticationManager(
    private val tokenDecoder: TokenDecoder,
) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: SpringAuthentication): Mono<SpringAuthentication> =
        mono { doAuthenticate(authentication) }

    private suspend fun doAuthenticate(
        authentication: SpringAuthentication,
    ): SpringAuthentication {
        val credentials = authentication.credentials.toString()
        val token = AccessToken(credentials)
        val payload = tokenDecoder.decode(token)
        return UsernamePasswordAuthenticationToken(
            payload.userId, // principal
            null, // credentials
            null, // authorities
        )
    }
}
