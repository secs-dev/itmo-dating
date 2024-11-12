package ru.ifmo.se.dating.container

import org.testcontainers.containers.PostgreSQLContainer

class Postgres private constructor() : AutoCloseable {
    private val container = PostgreSQLContainer(DOCKER_IMAGE_NAME)

    private val host: String get() = container.host

    private val port: Int get() = container.firstMappedPort

    private val databaseName: String get() = container.databaseName

    val jdbcUrl: String get() = container.jdbcUrl

    val r2dbcUrl: String get() = "r2dbc:postgresql://$host:$port/$databaseName"

    val username: String get() = container.username

    val password: String get() = container.password

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
