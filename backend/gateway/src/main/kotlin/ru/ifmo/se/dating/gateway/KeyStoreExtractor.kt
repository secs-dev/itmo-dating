package ru.ifmo.se.dating.gateway

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Profile("!test")
@Component
class KeyStoreExtractor(
    @Value("\${spring.cloud.consul.tls.certificate-path}")
    private val certificatePath: String,
) {
    @PostConstruct
    fun extractCertificate() {
        extract(certificatePath)
    }

    companion object {
        private fun extract(path: String) {
            val inputStream = KeyStoreExtractor::class.java.getResourceAsStream(path)!!
            File(path).parentFile?.mkdirs()
            Files.copy(inputStream, Paths.get(path), StandardCopyOption.REPLACE_EXISTING)
        }
    }
}
