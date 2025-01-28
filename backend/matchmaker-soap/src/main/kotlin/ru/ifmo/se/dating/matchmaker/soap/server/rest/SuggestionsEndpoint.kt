package ru.ifmo.se.dating.matchmaker.soap.server.rest

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.GetSuggestionsRequest
import ru.ifmo.se.dating.matchmaker.server.api.generated.SuggestionsApiDelegate
import ru.ifmo.se.dating.matchmaker.soap.client.soap.MatchmakerSoapClient

@Controller
class SuggestionsEndpoint(private val client: MatchmakerSoapClient) : SuggestionsApiDelegate {
    override fun suggestionsGet(limit: Long): ResponseEntity<List<Long>> = client
        .getSuggestions(
            SpringHeaders.getAuthorization(),
            GetSuggestionsRequest().also { it.limit = limit.toInt() }
        )
        .personId
        .map { it.toLong() }
        .let { ResponseEntity.ok(it) }
}
