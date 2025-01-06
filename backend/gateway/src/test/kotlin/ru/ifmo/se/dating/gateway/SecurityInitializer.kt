package ru.ifmo.se.dating.gateway

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class SecurityInitializer :
    ApplicationContextInitializer<ConfigurableApplicationContext> {

    private val keyStorePassword: String = "testing-keystore-password"

    override fun initialize(ctx: ConfigurableApplicationContext) {
        TestPropertyValues.of(
            "server.ssl.key-store-password=$keyStorePassword",
        ).applyTo(ctx.environment)
    }
}
