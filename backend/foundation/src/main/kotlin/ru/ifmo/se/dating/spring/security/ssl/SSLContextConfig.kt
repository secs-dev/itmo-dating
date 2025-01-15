package ru.ifmo.se.dating.spring.security.ssl

import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
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

    @Value("\${server.ssl.protocol}")
    private val sslProtocol: String,
) {
    private val log = autoLog()

    @Bean
    fun sslContext(): SslContext {
        log.info("Loading an SSL Context...")

        log.info("Loading a keystore with type $keyStoreType...")
        val keystore = KeyStore.getInstance(keyStoreType)
        keyStore.inputStream.use { inputStream ->
            keystore.load(inputStream, keyStorePassword.toCharArray())
        }

        log.info("Creating a Key Manager...")
        val keyManager = KeyManagerFactory.getDefaultAlgorithm()
            .let { KeyManagerFactory.getInstance(it) }
            .apply { init(keystore, keyStorePassword.toCharArray()) }

        log.info("Creating a Trust Manager...")
        val trustManager = TrustManagerFactory.getDefaultAlgorithm()
            .let { TrustManagerFactory.getInstance(it) }
            .apply { init(keystore) }

        log.info("Building an SSL Context...")
        return SslContextBuilder.forClient()
            .keyManager(keyManager)
            .trustManager(trustManager)
            .protocols(sslProtocol)
            .build()
            .also { log.info("SSL Context was built") }
    }
}
