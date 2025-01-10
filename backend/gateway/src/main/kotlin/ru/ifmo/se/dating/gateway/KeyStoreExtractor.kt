package ru.ifmo.se.dating.gateway

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


@Component
class KeyStoreExtractor(
    @Value("\${spring.cloud.consul.tls.certificate-path}")
    private val certificatePath: String,

    @Value("\${spring.cloud.consul.tls.key-store-path}")
    private val keystorePath: String,
) {
    @PostConstruct
    fun extractCertificate() {
        extract(certificatePath)
        extract(keystorePath)
    }

    private fun extract(path: String) {
        val inputStream = javaClass.classLoader.getResourceAsStream(certificatePath)!!
        File(certificatePath).parentFile?.mkdirs()
        Files.copy(inputStream, Paths.get(certificatePath), StandardCopyOption.REPLACE_EXISTING)
    }
}
