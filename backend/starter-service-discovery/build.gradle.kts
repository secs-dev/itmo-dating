plugins {
    id("buildlogic.kotlin-library-conventions")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":starter-tls"))

    api(libs.org.springframework.boot.spring.boot.starter.actuator)
    api(libs.io.micrometer.micrometer.registry.prometheus)
    api(libs.org.springframework.cloud.spring.cloud.starter.consul.discovery)
    api(libs.org.springframework.cloud.spring.cloud.starter.loadbalancer)
}
