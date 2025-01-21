plugins {
    id("buildlogic.kotlin-library-conventions")
    kotlin("plugin.spring")
}

dependencies {
    api(libs.org.springframework.boot.spring.boot)
    api(libs.org.springframework.spring.web)
    api(libs.org.springframework.spring.webflux)
    implementation("io.netty:netty-handler:4.1.117.Final")
}
