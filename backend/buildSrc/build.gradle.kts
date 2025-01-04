plugins {
    kotlin("jvm") version "2.0.10"
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

kotlin {
    jvmToolchain(22)
}

dependencies {
    val kotlinVersion = "2.0.10"
    val jooqVersion = "3.19.15"

    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")

    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.7")

    implementation("org.openapitools:openapi-generator-gradle-plugin:7.9.0")

    implementation("org.jooq:jooq-codegen:$jooqVersion")
    implementation("org.jooq:jooq-codegen-gradle:$jooqVersion")

    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.3.5")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.6")
}
