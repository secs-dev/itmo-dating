package ru.ifmo.se.dating.gateway

import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import reactor.netty.http.client.HttpClient
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.TrustManagerFactory

@Configuration
class ClientSSLContextConfig(
    @Value("\${client.ssl.key-store-type}")
    private val keyStoreType: String,

    @Value("\${client.ssl.key-store}")
    private val keyStore: Resource,

    @Value("\${client.ssl.key-store-password}")
    private val keyStorePassword: String,

    @Value("\${client.ssl.protocol}")
    private val sslProtocol: String,
) {
    @Bean
    fun sslContext(): SslContext {
        val keystore = KeyStore.getInstance(keyStoreType)
        keyStore.inputStream.use { inputStream ->
            keystore.load(inputStream, keyStorePassword.toCharArray())
        }

        val keyManager = KeyManagerFactory.getDefaultAlgorithm()
            .let { KeyManagerFactory.getInstance(it) }
            .apply { init(keystore, keyStorePassword.toCharArray()) }

        val trustManager = TrustManagerFactory.getDefaultAlgorithm()
            .let { TrustManagerFactory.getInstance(it) }
            .apply { init(keystore) }

        return SslContextBuilder.forClient()
            .keyManager(keyManager)
            .trustManager(trustManager)
            .protocols(sslProtocol)
            .build()
    }

    @Bean
    fun httpClient(ssl: SslContext): HttpClient = HttpClient.create()
        .secure { sslSpec -> sslSpec.sslContext(ssl) }
}
