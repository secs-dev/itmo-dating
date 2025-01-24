plugins {
    id("buildlogic.spring-conventions")
}

dependencies {
    implementation(libs.org.springframework.boot.spring.boot)
    implementation(libs.org.springframework.boot.spring.boot.starter.web)
    implementation(libs.org.springframework.cloud.spring.cloud.config.server)

    implementation(project(":starter-monitoring"))
    implementation(project(":starter-service-discovery"))
    implementation(project(":starter-tls"))

    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testImplementation(libs.junit.junit)
    testImplementation(libs.org.testcontainers.vault)
}
