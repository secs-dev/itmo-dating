package ru.ifmo.se.dating.spring.api

import io.netty.handler.ssl.SslContext
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientBuilderConfig(
    private val ssl: SslContext,
) {
    @Bean
    @LoadBalanced
    fun webClientBuilderLoadBalanced(): WebClient.Builder = WebClient.builder()
        .clientConnector(connector())

    @Bean
    @Primary
    fun webClientBuilder(): WebClient.Builder = WebClient.builder()
        .clientConnector(connector())

    private fun connector() =
        ReactorClientHttpConnector(
            HttpClient.create()
                .secure { sslContextSpec -> sslContextSpec.sslContext(ssl) }
        )
}
