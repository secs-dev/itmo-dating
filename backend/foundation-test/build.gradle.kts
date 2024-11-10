plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    api(libs.org.springframework.boot.spring.boot.starter.test)

    api(libs.junit.junit)
    api(libs.org.testcontainers.postgresql)
    api(libs.org.testcontainers.r2dbc)
    api(libs.org.testcontainers.junit.jupiter)
    api(libs.io.projectreactor.reactor.test)
}
