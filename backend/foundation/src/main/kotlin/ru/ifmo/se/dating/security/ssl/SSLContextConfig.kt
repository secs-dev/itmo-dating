package ru.ifmo.se.dating.security.ssl

import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.TrustManagerFactory

@Configuration
class SSLContextConfig(
    @Value("\${server.ssl.key-store-type}")
    private val keyStoreType: String,

    @Value("\${server.ssl.key-store}")
    private val keyStore: Resource,

    @Value("\${server.ssl.key-store-password}")
    private val keyStorePassword: String,

    @Value("\${server.ssl.trust-store-type}")
    private val trustStoreType: String,

    @Value("\${server.ssl.trust-store}")
    private val trustStore: Resource,

    @Value("\${server.ssl.trust-store-password}")
    private val trustStorePassword: String,

    @Value("\${server.ssl.protocol}")
    private val sslProtocol: String,
) {
    @Bean
    fun sslContext(): SslContext {
        val keystore = KeyStore.getInstance(keyStoreType)
        keyStore.inputStream.use { inputStream ->
            keystore.load(inputStream, keyStorePassword.toCharArray())
        }

        val truststore = KeyStore.getInstance(trustStoreType)
        trustStore.inputStream.use { inputStream ->
            truststore.load(inputStream, trustStorePassword.toCharArray())
        }

        val keyManager = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        keyManager.init(keystore, keyStorePassword.toCharArray())

        val trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManager.init(truststore)

        return SslContextBuilder.forClient()
            .keyManager(keyManager)
            .trustManager(trustManager)
            .protocols(sslProtocol)
            .build()
    }
}
