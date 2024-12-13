package ru.ifmo.se.dating.spring.security.auth

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import ru.ifmo.se.dating.exception.AuthenticationException
import ru.ifmo.se.dating.exception.GenericException
import ru.ifmo.se.dating.spring.exception.SpringGenericExceptionHandler

@Component
class DomainExceptionMarshalling(
    private val handler: SpringGenericExceptionHandler,
    private val jackson: ObjectMapper,
) {
    suspend fun write(
        response: ServerHttpResponse,
        exception: GenericException,
    ) {
        val entity = handler.toResponseEntity(exception)
        val message = jackson.writeValueAsBytes(entity.body)
        val buffer = response.bufferFactory().wrap(message)
        response.statusCode = entity.statusCode
        response.beforeCommit { response.writeWith(Mono.just(buffer)) }
    }
}

@Component
class SpringJwtContextRepository(
    private val marshalling: DomainExceptionMarshalling,
    private val authManager: SpringAuthenticationManager,
) : ServerSecurityContextRepository {
    private val headerName = HttpHeaders.AUTHORIZATION
    private val bearerPrefix = "Bearer "

    override fun save(
        exchange: ServerWebExchange,
        context: SecurityContext,
    ): Mono<Void> = TODO("Not yet implemented")

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> =
        Mono.defer { mono { doLoad(exchange) } }

    private suspend fun doLoad(exchange: ServerWebExchange): SecurityContext? =
        try {
            extractBearer(exchange)
                .let { UsernamePasswordAuthenticationToken(it, it) }
                .let { authManager.authenticate(it).awaitSingle() }
                .let { SecurityContextImpl(it) }
        } catch (exception: GenericException) {
            AuthenticationException(exception.message!!, exception)
                .let { marshalling.write(exchange.response, it) }
            null
        }

    private fun extractBearer(exchange: ServerWebExchange): String {
        val count = exchange.request.headers.getOrEmpty(headerName).size
        if (count != 1) {
            throw AuthenticationException(
                "expected only 1 header '$headerName', got $count",
            )
        }
        val bearer = exchange.request.headers[headerName]!![0]
        if (!bearer.startsWith(bearerPrefix)) {
            throw AuthenticationException(
                "expected format 'Bearer <your jwt token>'",
            )
        }
        return bearer.substringAfter(bearerPrefix)
    }
}
