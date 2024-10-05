plugins {
    kotlin("jvm")
}

group = "ru.ifmo.se.dating"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(20)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(20)
    }
}
