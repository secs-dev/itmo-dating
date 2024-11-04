package ru.ifmo.se.dating.matchmaker

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import ru.ifmo.se.dating.container.Postgres

class DatabaseInitializer :
    ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val postgres: Postgres = Postgres.start()

    override fun initialize(ctx: ConfigurableApplicationContext) {
        TestPropertyValues.of(
            "spring.datasource.url=${postgres.jdbcUrl()}",
            "spring.datasource.username=${postgres.username()}",
            "spring.datasource.password=${postgres.password()}",
        ).applyTo(ctx.environment)
    }
}
