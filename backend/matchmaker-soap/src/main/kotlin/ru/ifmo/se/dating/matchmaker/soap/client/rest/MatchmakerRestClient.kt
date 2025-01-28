package ru.ifmo.se.dating.matchmaker.soap.client.rest

import org.springframework.web.client.RestClient
import ru.ifmo.se.dating.matchmaker.client.generated.apis.PeopleApi
import ru.ifmo.se.dating.matchmaker.client.generated.apis.StatisticsApi
import ru.ifmo.se.dating.matchmaker.client.generated.apis.SuggestionsApi

class MatchmakerRestClient(private val client: RestClient) {
    fun people(authorization: String? = null) =
        PeopleApi(clientWithHeaders(authorization))

    fun statistics(authorization: String? = null) =
        StatisticsApi(clientWithHeaders(authorization))

    fun suggestions(authorization: String? = null) =
        SuggestionsApi(clientWithHeaders(authorization))

    private fun clientWithHeaders(authorization: String? = null) =
        client.mutate()
            .apply { client ->
                authorization?.let {
                    client.defaultHeader("Authorization", "Bearer $it")
                }
            }
            .build()
}
