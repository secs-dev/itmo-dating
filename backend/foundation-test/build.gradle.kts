plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    api(libs.org.springframework.boot.spring.boot.starter.test)
    api(libs.org.testcontainers.postgresql)
    api(libs.junit.junit)
}
