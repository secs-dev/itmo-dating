package ru.ifmo.se.dating.spring.logging

import io.github.numichi.reactive.logger.coroutine.modifyMdc
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class RequestLoggingFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        chain.filter(exchange)
            .contextWrite { ctx -> ctx.modifyMdc { mdc -> mdc + keys(exchange.request) } }

    private fun keys(request: ServerHttpRequest) = mapOf(
        "request_id" to request.id.toString(),
        "remote_address" to (request.remoteAddress?.toString() ?: "null"),
        "method" to request.method.toString(),
        "path" to request.path.toString(),
    )
}
