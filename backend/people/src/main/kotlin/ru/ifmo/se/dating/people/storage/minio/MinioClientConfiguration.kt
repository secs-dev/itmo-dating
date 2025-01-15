package ru.ifmo.se.dating.people.storage.minio

import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.ifmo.se.dating.logging.Log
import ru.ifmo.se.dating.logging.Log.Companion.autoLog

@Configuration
class MinioClientConfiguration {
    private val isSecure = false

    private val log = Log.autoLog()

    @Bean
    @Suppress("LongParameterList")
    fun minioClient(
        @Value("\${storage.s3.url}")
        url: String,

        @Value("\${storage.s3.port}")
        port: Int,

        @Value("\${storage.s3.username}")
        username: String,

        @Value("\${storage.s3.password}")
        password: String,
    ): MinioClient = MinioClient.builder()
        .endpoint(url, port, isSecure)
        .credentials(username, password)
        .build()
        .also { client ->
            val names = client.listBuckets().joinToString(", ") { "'${it.name()}'" }
            log.info("Initialized MinIO client. Found buckets: $names")
        }
}
