package ru.ifmo.se.dating.spring.security

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import ru.ifmo.se.dating.security.auth.User

object SpringSecurityContext {
    suspend fun principal(): User.Id {
        val context = context()
        val auth = context.authentication as UsernamePasswordAuthenticationToken
        val user = auth.principal as User.Id
        return user
    }

    private suspend fun context(): SecurityContext =
        ReactiveSecurityContextHolder.getContext().awaitSingle()
}
