package ru.ifmo.se.dating.people;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
