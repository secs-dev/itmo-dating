package ru.ifmo.se.dating.matchmaker;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.ToString;
import ru.ifmo.se.dating.container.Postgres;

@ToString
public final class DatabaseInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final Postgres postgres;

    DatabaseInitializer() {
        this.postgres = Postgres.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=%s".formatted(postgres.jdbcUrl()),
                "spring.datasource.username=%s".formatted(postgres.username()),
                "spring.datasource.password=%s".formatted(postgres.password()))
                .applyTo(applicationContext.getEnvironment());
    }
}