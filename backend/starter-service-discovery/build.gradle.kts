plugins {
    id("buildlogic.kotlin-library-conventions")
    kotlin("plugin.spring")
}

dependencies {
    api(libs.org.springframework.boot.spring.boot.starter.actuator)
    api(libs.org.springframework.cloud.spring.cloud.starter.consul.discovery)
    api(libs.org.springframework.cloud.spring.cloud.starter.loadbalancer)
}
