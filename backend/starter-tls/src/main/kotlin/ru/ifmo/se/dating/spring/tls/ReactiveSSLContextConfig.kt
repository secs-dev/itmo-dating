package ru.ifmo.se.dating.spring.tls

import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.TrustManagerFactory

@Configuration
class ReactiveSSLContextConfig(
    @Value("\${client.ssl.protocol}")
    private val sslProtocol: String,
) {
    @Bean
    fun reactiveSSLContext(
        keyManager: KeyManagerFactory,
        trustManager: TrustManagerFactory,
    ): SslContext = SslContextBuilder.forClient()
        .keyManager(keyManager)
        .trustManager(trustManager)
        .protocols(sslProtocol)
        .build()
}
