package ru.ifmo.se.dating.people.container

import org.testcontainers.containers.MinIOContainer

class MinIO private constructor() : AutoCloseable {
    private val container = MinIOContainer(DOCKER_IMAGE_NAME)

    val endpoint: String get() = container.s3URL

    val port: Int get() = container.firstMappedPort

    val username: String get() = container.userName

    val password: String get() = container.password

    override fun close() {
        container.stop()
        container.close()
    }

    companion object {
        private const val DOCKER_IMAGE_NAME = "minio/minio"

        fun start(): MinIO {
            val postgres = MinIO()
            postgres.container.start()
            return postgres
        }
    }
}
