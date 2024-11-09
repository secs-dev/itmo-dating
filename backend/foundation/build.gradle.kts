plugins {
    id("buildlogic.kotlin-library-conventions")
    id("buildlogic.jooq-conventions")
}

dependencies {
    api(libs.io.swagger.core.v3.swagger.annotations)
    api(libs.io.swagger.core.v3.swagger.models)
    api(libs.org.openapitools.jackson.databind.nullable)
    api(libs.org.springdoc.springdoc.openapi.starter.webmvc.ui)

    api(libs.org.springframework.spring.web)
    api(libs.org.springframework.spring.context)

    api(libs.org.springframework.boot.spring.boot)
    api(libs.org.springframework.boot.spring.boot.starter.web)

    api(libs.org.springframework.boot.spring.boot.starter.jdbc)
    api(libs.org.liquibase.liquibase.core)
    api(libs.org.postgresql.postgresql)

    api(libs.com.fasterxml.jackson.core.jackson.databind)
    api(libs.jakarta.validation.jakarta.validation.api)

    api(libs.org.jooq.jooq)
    api(libs.org.jooq.jooq.kotlin)

    jooqCodegen("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")
    jooqCodegen(libs.org.jooq.jooq.meta.extensions)
    jooqCodegen(libs.org.jooq.jooq.meta.kotlin)
    jooqCodegen(libs.org.postgresql.postgresql)
    jooqCodegen(libs.org.testcontainers.postgresql)
    jooqCodegen(libs.org.testcontainers.testcontainers)
}
