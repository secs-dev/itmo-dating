plugins {
    kotlin("jvm") version "1.9.24"
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
