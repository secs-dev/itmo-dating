package ru.ifmo.se.dating.spring.tls

import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.netty.http.client.HttpClient
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.TrustManagerFactory

@Configuration
class ReactiveSSLContextConfig(
    @Value("\${client.ssl.protocol}")
    private val sslProtocol: String,
) {
    @Bean
    fun reactiveSSLContext(
        @Qualifier("clientKeyManager")
        keyManager: KeyManagerFactory,

        @Qualifier("clientTrustManager")
        trustManager: TrustManagerFactory,
    ): SslContext = SslContextBuilder.forClient()
        .keyManager(keyManager)
        .trustManager(trustManager)
        .protocols(sslProtocol)
        .build()
        .also { println("Created reactive SSL Context") }

    @Bean
    fun httpClient(ssl: SslContext): HttpClient = HttpClient.create()
        .secure { sslSpec -> sslSpec.sslContext(ssl) }
}
