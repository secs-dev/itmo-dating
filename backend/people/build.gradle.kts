@file:Suppress("UNCHECKED_CAST")

plugins {
    id("buildlogic.foundation-conventions")
}

dependencies {
    implementation(libs.io.minio.minio)
    implementation(project(":foundation"))
    testImplementation(project(":foundation-test"))
}

val serviceName = "people"

(extra["generateOAPIServer"] as (String) -> Unit)(serviceName)
(extra["generateJOOQ"] as (String) -> Unit)(serviceName)
(extra["generateOAPIClient"] as (String) -> Unit)("matchmaker")
