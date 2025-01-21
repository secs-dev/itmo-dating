package ru.ifmo.se.dating.container

import org.testcontainers.vault.VaultContainer

class Vault private constructor(val token: String) : AutoCloseable {
    private val container = VaultContainer(DOCKER_IMAGE_NAME)

    val host: String get() = container.host

    val port: Int get() = container.firstMappedPort

    override fun close() {
        container.stop()
        container.close()
    }

    companion object {
        private const val DOCKER_IMAGE_NAME = "hashicorp/vault"

        fun start(token: String): Vault {
            val vault = Vault(token)
            vault.container.start()
            return vault
        }
    }
}
