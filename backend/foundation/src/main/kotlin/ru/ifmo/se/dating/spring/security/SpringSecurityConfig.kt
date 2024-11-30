package ru.ifmo.se.dating.spring.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SpringSecurityConfig(
    private val authManager: SpringAuthenticationManager,
    private val contextRepository: SpringJwtContextRepository,
    private val securedPaths: SpringSecuredPaths,
) {
    @Bean
    fun chain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http
            .authenticationManager(authManager)
            .securityContextRepository(contextRepository)
            .authorizeExchange {
                it.matchers(securedPaths.matcher).authenticated()
                it.anyExchange().permitAll()
            }
            .cors { }
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .build()
}
