plugins {
    id("buildlogic.kotlin-library-conventions")
    id("buildlogic.jooq-conventions")
}

dependencies {
    api(libs.org.springframework.boot.spring.boot)
    api(libs.org.springframework.boot.spring.boot.starter.webflux)
    api(libs.org.springframework.boot.spring.boot.starter.data.r2dbc)

    api(libs.org.springframework.spring.web)
    api(libs.org.springframework.spring.context)

    api(libs.org.springdoc.springdoc.openapi.starter.webflux.ui)

    api(libs.io.swagger.core.v3.swagger.annotations)
    api(libs.io.swagger.core.v3.swagger.models)
    api(libs.org.openapitools.jackson.databind.nullable)

    api(libs.jakarta.validation.jakarta.validation.api)
    api(libs.com.fasterxml.jackson.core.jackson.databind)

    api(libs.org.jetbrains.kotlinx.kotlinx.coroutines.reactor)
    api(libs.io.projectreactor.kotlin.reactor.kotlin.extensions)

    api(libs.org.jooq.jooq)
    api(libs.org.jooq.jooq.kotlin)
    api(libs.org.jooq.jooq.meta.extensions)
    api(libs.org.jooq.jooq.meta.kotlin)

    api(libs.org.liquibase.liquibase.core)
    api(libs.org.postgresql.postgresql)
    api(libs.org.postgresql.r2dbc.postgresql)
}
