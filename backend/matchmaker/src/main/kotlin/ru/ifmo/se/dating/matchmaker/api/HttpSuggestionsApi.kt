package ru.ifmo.se.dating.matchmaker.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.api.generated.SuggestionsApiDelegate
import ru.ifmo.se.dating.matchmaker.logic.AttitudeService
import ru.ifmo.se.dating.spring.security.SpringSecurityContext

@Controller
class HttpSuggestionsApi(
    private val service: AttitudeService,
) : SuggestionsApiDelegate {
    override fun suggestionsGet(limit: Long): ResponseEntity<Flow<Long>> = flow {
        val client = SpringSecurityContext.principal()
        service.suggestions(client, limit.toInt())
            .map { it.number.toLong() }
            .collect { emit(it) }
    }.let { ResponseEntity.ok(it) }
}
