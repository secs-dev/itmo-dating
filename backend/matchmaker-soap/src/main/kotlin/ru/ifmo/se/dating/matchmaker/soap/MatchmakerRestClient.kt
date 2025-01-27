package ru.ifmo.se.dating.matchmaker.soap

import org.springframework.web.client.RestClient
import ru.ifmo.se.dating.matchmaker.client.generated.apis.PeopleApi
import ru.ifmo.se.dating.matchmaker.client.generated.apis.StatisticsApi
import ru.ifmo.se.dating.matchmaker.client.generated.apis.SuggestionsApi

class MatchmakerRestClient(client: RestClient) {
    val people = PeopleApi(client)
    val statistics = StatisticsApi(client)
    val suggestions = SuggestionsApi(client)
}
