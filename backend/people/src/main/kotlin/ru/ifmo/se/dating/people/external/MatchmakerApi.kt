package ru.ifmo.se.dating.people.external

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import ru.ifmo.se.dating.matchmaker.client.generated.apis.PeopleApi
import ru.ifmo.se.dating.matchmaker.client.model.generated.PersonUpdateMessage

@Component
class MatchmakerApi(
    @Value("\${itmo-dating.matchmaker.url}")
    private val url: String,

    @LoadBalanced
    webClient: WebClient.Builder,
) {
    private val people = PeopleApi(webClient.baseUrl(url).build())

    suspend fun putPersonUpdate(personId: Long, update: PersonUpdateMessage) =
        people.peoplePersonIdPut(personId, update).awaitSingle()
}
