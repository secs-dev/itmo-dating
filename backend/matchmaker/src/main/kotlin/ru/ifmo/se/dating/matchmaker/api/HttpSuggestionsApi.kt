package ru.ifmo.se.dating.matchmaker.api

import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.api.generated.SuggestionsApiDelegate

@Controller
internal class HttpSuggestionsApi : SuggestionsApiDelegate {
    override fun suggestionsGet(limit: Long): ResponseEntity<Flow<Long>> =
        ResponseEntityStub.create()
}
