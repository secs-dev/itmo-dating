plugins {
    id("buildlogic.kotlin-library-conventions")
    id("buildlogic.jooq-conventions")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":starter-service-discovery"))
    api(project(":starter-tls"))

    api(libs.org.springframework.boot.spring.boot)
    api(libs.org.springframework.boot.spring.boot.starter.webflux)
    api(libs.org.springframework.boot.spring.boot.starter.data.r2dbc)
    api(libs.org.springframework.boot.spring.boot.starter.security)
    api(libs.org.springframework.boot.spring.boot.starter.actuator)

    api(libs.org.springframework.spring.web)
    api(libs.org.springframework.spring.context)

    api(libs.org.springframework.cloud.spring.cloud.starter.consul.discovery)

    api(libs.org.springdoc.springdoc.openapi.starter.webflux.ui)

    api(libs.io.swagger.core.v3.swagger.annotations)
    api(libs.io.swagger.core.v3.swagger.models)
    api(libs.org.openapitools.jackson.databind.nullable)

    api(libs.io.jsonwebtoken.jjwt.api)
    runtimeOnly(libs.io.jsonwebtoken.jjwt.impl)
    runtimeOnly(libs.io.jsonwebtoken.jjwt.jackson)

    api(libs.jakarta.validation.jakarta.validation.api)
    api(libs.com.fasterxml.jackson.core.jackson.databind)
    api(libs.com.fasterxml.jackson.module.jackson.module.kotlin)

    api(libs.org.jetbrains.kotlinx.kotlinx.coroutines.reactor)
    api(libs.io.projectreactor.kotlin.reactor.kotlin.extensions)

    api(libs.org.jooq.jooq)
    api(libs.org.jooq.jooq.kotlin)
    api(libs.org.jooq.jooq.meta.extensions)
    api(libs.org.jooq.jooq.meta.kotlin)

    api(libs.org.liquibase.liquibase.core)
    api(libs.org.postgresql.postgresql)
    api(libs.org.postgresql.r2dbc.postgresql)

    testImplementation(libs.junit.junit)
}
