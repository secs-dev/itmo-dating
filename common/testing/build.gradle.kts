plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    api(libs.org.testcontainers.postgresql)
    api(libs.junit.junit)
}
