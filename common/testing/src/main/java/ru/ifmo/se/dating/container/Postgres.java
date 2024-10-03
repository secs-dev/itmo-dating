package ru.ifmo.se.dating.container;

import org.testcontainers.containers.PostgreSQLContainer;

import lombok.ToString;

@ToString
public final class Postgres implements AutoCloseable {
    private static final String DOCKER_IMAGE_NAME = "postgres";

    private final PostgreSQLContainer<?> container;

    private Postgres() {
        this.container = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME);
    }

    public String jdbcUrl() {
        return container.getJdbcUrl();
    }

    public String username() {
        return container.getUsername();
    }

    public String password() {
        return container.getPassword();
    }

    @Override
    public void close() throws Exception {
        container.stop();
        container.close();
    }

    public static Postgres start() {
        final var postgres = new Postgres();
        postgres.container.start();
        return postgres;
    }
}
