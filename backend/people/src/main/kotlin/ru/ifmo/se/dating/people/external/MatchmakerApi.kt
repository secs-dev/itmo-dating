package ru.ifmo.se.dating.people.external

import io.netty.handler.ssl.SslContext
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import ru.ifmo.se.dating.matchmaker.client.generated.apis.PeopleApi
import ru.ifmo.se.dating.matchmaker.client.model.generated.PersonUpdateMessage

@Component
class MatchmakerApi(
    @Value("\${service.matchmaker.url}")
    private val url: String,

    ssl: SslContext,
) {
    private val people = PeopleApi(
        WebClient.builder()
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create()
                        .secure { sslContextSpec -> sslContextSpec.sslContext(ssl) }
                ),
            )
            .baseUrl(url)
            .build(),
    )

    suspend fun putPersonUpdate(personId: Long, update: PersonUpdateMessage) =
        people.peoplePersonIdPut(personId, update).awaitSingle()
}
