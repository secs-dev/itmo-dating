package ru.ifmo.se.dating.spring.tls

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

@Configuration
class BlockingSSLContextConfig(
    @Value("\${client.ssl.protocol}")
    private val sslProtocol: String,
) {
    @Bean
    fun blockingSSLContext(
        @Qualifier("clientKeyManager")
        keyManager: KeyManagerFactory,

        @Qualifier("clientTrustManager")
        trustManager: TrustManagerFactory,
    ): SSLContext =
        SSLContext
            .getInstance(sslProtocol)
            .also { it.init(keyManager.keyManagers, trustManager.trustManagers, null) }
            .also { println("Created blocking SSL Context") }


}
