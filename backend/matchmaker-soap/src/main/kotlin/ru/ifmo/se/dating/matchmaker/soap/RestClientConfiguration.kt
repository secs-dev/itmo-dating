package ru.ifmo.se.dating.matchmaker.soap

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.web.client.RestClientSsl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestClient

@Configuration
class RestClientConfiguration {
    @Bean
    fun matchmakerRestClient(
        @Value("\${itmo-dating.matchmaker.url}")
        baseUrl: String,

        ssl: RestClientSsl,
    ): MatchmakerRestClient = RestClient.builder()
        .baseUrl("$baseUrl/api")
        .messageConverters { it.add(MappingJackson2HttpMessageConverter()) }
        .apply(ssl.fromBundle("internal"))
        .build()
        .let { MatchmakerRestClient(it) }
}
