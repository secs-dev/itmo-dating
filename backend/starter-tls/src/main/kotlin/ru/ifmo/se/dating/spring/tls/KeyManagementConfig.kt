package ru.ifmo.se.dating.spring.tls

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.TrustManagerFactory

@Configuration
class KeyManagementConfig(
    @Value("\${client.ssl.key-store-type}")
    private val keyStoreType: String,

    @Value("\${client.ssl.key-store}")
    private val keyStore: Resource,

    @Value("\${client.ssl.key-store-password}")
    private val keyStorePassword: String,
) {
    @Bean("clientKeyStore")
    fun clientKeyStore(): KeyStore {
        val keystore = KeyStore.getInstance(keyStoreType)
        keyStore.inputStream.use { inputStream ->
            keystore.load(inputStream, keyStorePassword.toCharArray())
        }
        return keystore
    }

    @Bean("clientKeyManager")
    fun clientKeyManager(@Qualifier("clientKeyStore") keystore: KeyStore): KeyManagerFactory =
        KeyManagerFactory.getDefaultAlgorithm()
            .let { KeyManagerFactory.getInstance(it) }
            .apply { init(keystore, keyStorePassword.toCharArray()) }

    @Bean("clientTrustManager")
    fun clientTrustManager(@Qualifier("clientKeyStore") keystore: KeyStore): TrustManagerFactory =
        TrustManagerFactory.getDefaultAlgorithm()
            .let { TrustManagerFactory.getInstance(it) }
            .apply { init(keystore) }
}
