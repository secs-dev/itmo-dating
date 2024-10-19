plugins {
    kotlin("jvm") version "2.0.10"
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin.coreLibrariesVersion}")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${kotlin.coreLibrariesVersion}")

    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.7")

    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.3.4")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.6")
}
