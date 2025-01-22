package ru.ifmo.se.dating.people

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import ru.ifmo.se.dating.people.container.MinIO

class MinIOInitializer :
    ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val minio: MinIO = MinIO.start()

    override fun initialize(ctx: ConfigurableApplicationContext) {
        TestPropertyValues.of(
            "itmo-dating.s3.host=${minio.endpoint}",
            "itmo-dating.s3.port=${minio.port}",
            "itmo-dating.s3.username=${minio.username}",
            "itmo-dating.s3.password=${minio.password}",
        ).applyTo(ctx.environment)
    }
}
