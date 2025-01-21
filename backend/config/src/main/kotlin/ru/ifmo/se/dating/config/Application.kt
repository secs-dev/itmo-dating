package ru.ifmo.se.dating.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["ru.ifmo.se.dating"])
@EnableConfigServer
class Application

fun main(args: Array<String>) {
    runApplication<Application>(args = args)
}
