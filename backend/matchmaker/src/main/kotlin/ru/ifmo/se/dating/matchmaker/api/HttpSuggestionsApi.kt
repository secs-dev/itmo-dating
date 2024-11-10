package ru.ifmo.se.dating.matchmaker.api

import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.api.generated.SuggestionsApiDelegate

@Service
internal class HttpSuggestionsApi : SuggestionsApiDelegate {
    override fun suggestionsGet(limit: Long): ResponseEntity<Flow<Long>> =
        ResponseEntityStub.create()
}
