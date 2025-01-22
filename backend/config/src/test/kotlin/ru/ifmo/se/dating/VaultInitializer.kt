package ru.ifmo.se.dating

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import ru.ifmo.se.dating.container.Vault

class VaultInitializer :
    ApplicationContextInitializer<ConfigurableApplicationContext> {

    private val token: String = "test-vault-token"

    private lateinit var vault: Vault

    override fun initialize(ctx: ConfigurableApplicationContext) {
        vault = Vault.start(token = token)

        TestPropertyValues.of(
            "spring.cloud.config.server.vault.host=${vault.host}",
            "spring.cloud.config.server.vault.port=${vault.port}",
            "spring.cloud.config.server.vault.token=$token",
        ).applyTo(ctx.environment)
    }
}
