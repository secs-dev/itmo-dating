package ru.ifmo.se.dating.gateway

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class CorsFilter : WebFilter {
    private val allowMethods = listOf(
        HttpMethod.GET,
        HttpMethod.PUT,
        HttpMethod.POST,
        HttpMethod.PATCH,
        HttpMethod.DELETE,
        HttpMethod.OPTIONS,
    )

    private val exposeHeaders = listOf(
        "DNT",
        "X-CustomHeader",
        "Keep-Alive",
        "User-Agent",
        "X-Requested-With",
        "If-Modified-Since",
        "Cache-Control",
        "Content-Type",
        "Content-Range",
        "Range",
    )

    override fun filter(ctx: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        ctx.response.headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        ctx.response.headers.add(
            HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
            allowMethods.joinToString(", ") { it.name() },
        )
        ctx.response.headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*")

        if (ctx.request.method == HttpMethod.OPTIONS) {
            ctx.response.headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, 1_728_000.toString())
            ctx.response.statusCode = HttpStatus.NO_CONTENT
            return Mono.empty()
        }

        ctx.response.headers.add(
            HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
            exposeHeaders.joinToString(","),
        )
        return chain.filter(ctx) ?: Mono.empty()
    }
}
