@file:Suppress("UNCHECKED_CAST")

plugins {
    id("buildlogic.foundation-conventions")
}

dependencies {
    implementation(libs.commons.codec.commons.codec)
    implementation(project(":foundation"))
    testImplementation(project(":foundation-test"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.+")
}

val serviceName = "authik"

(extra["generateOAPIServer"] as (String) -> Unit)(serviceName)
(extra["generateJOOQ"] as (String) -> Unit)(serviceName)
