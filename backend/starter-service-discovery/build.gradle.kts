plugins {
    id("buildlogic.kotlin-library-conventions")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":starter-tls"))
    api(project(":starter-monitoring"))

    api(libs.org.springframework.cloud.spring.cloud.starter.consul.discovery)
    api(libs.org.springframework.cloud.spring.cloud.starter.loadbalancer)
}
