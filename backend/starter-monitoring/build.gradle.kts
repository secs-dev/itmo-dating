plugins {
    id("buildlogic.kotlin-library-conventions")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":starter-tls"))

    api(libs.org.springframework.boot.spring.boot.starter.actuator)
    api(libs.io.micrometer.micrometer.registry.prometheus)

    api(libs.com.github.loki4j.loki.logback.appender)
    api(libs.io.github.numichi.reactive.logger)
}
