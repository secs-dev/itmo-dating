package ru.ifmo.se.dating.container

import org.testcontainers.containers.PostgreSQLContainer

class Postgres private constructor() : AutoCloseable {
    private val container = PostgreSQLContainer(DOCKER_IMAGE_NAME)

    fun jdbcUrl(): String {
        return container.jdbcUrl
    }

    fun username(): String {
        return container.username
    }

    fun password(): String {
        return container.password
    }

    @Throws(Exception::class)
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
