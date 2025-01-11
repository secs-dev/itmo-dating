package ru.ifmo.se.dating.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["ru.ifmo.se.dating"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(args = args)
}
