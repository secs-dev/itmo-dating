plugins {
    kotlin("jvm") version "2.0.10"
}

group = "ru.ifmo.se.dating"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(20)
}
