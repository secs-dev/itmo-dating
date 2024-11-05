@file:Suppress("UNCHECKED_CAST")

plugins {
    id("buildlogic.foundation-conventions")
}

dependencies {
    implementation(project(":foundation"))
    testImplementation(project(":foundation-test"))
}

(extra["generateOAPIServer"] as (String) -> Unit)("people")
