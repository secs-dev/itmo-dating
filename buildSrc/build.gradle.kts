plugins {
    kotlin("jvm") version "2.0.20"
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    val kotlinVersion = kotlin.coreLibrariesVersion
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}
