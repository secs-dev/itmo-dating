package ru.ifmo.se.dating.spring.security.auth

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.security.auth.User

object SpringSecurityContext {
    private val log = autoLog()

    suspend fun principal(): User.Id = runCatching {
        val context = context()
        val auth = context.authentication as UsernamePasswordAuthenticationToken
        val user = auth.principal as User.Id
        user
    }
        .onSuccess { user -> log.info("Authenticated a user with id $user") }
        .onFailure { e -> log.error("Failed to load security context: ${e.message}", e) }
        .getOrThrow()

    private suspend fun context(): SecurityContext =
        ReactiveSecurityContextHolder.getContext().awaitSingle()
}
