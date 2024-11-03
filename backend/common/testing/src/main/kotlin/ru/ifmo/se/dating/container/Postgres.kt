package ru.ifmo.se.dating.container

import org.testcontainers.containers.PostgreSQLContainer

class Postgres private constructor() : AutoCloseable {
    private val container = PostgreSQLContainer(DOCKER_IMAGE_NAME)

    fun jdbcUrl(): String =
        container.jdbcUrl

    fun username(): String =
        container.username

    fun password(): String =
        container.password

    override fun close() {
        container.stop()
        container.close()
    }

    companion object {
        private const val DOCKER_IMAGE_NAME = "postgres"

        fun start(): Postgres {
            val postgres = Postgres()
            postgres.container.start()
            return postgres
        }
    }
}
