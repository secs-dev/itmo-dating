package ru.ifmo.se.dating.spring.security.auth

import org.springframework.security.web.server.util.matcher.AndServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher

typealias Path = PathPatternParserServerWebExchangeMatcher
typealias Not = NegatedServerWebExchangeMatcher
typealias And = AndServerWebExchangeMatcher

interface SpringSecuredPaths {
    val matcher: ServerWebExchangeMatcher
}
