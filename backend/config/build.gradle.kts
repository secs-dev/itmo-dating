plugins {
    id("buildlogic.spring-conventions")
}

dependencies {
    implementation(project(":starter-service-discovery"))
    implementation(project(":starter-tls"))

    implementation(libs.org.springframework.boot.spring.boot)
    implementation(libs.org.springframework.boot.spring.boot.starter.web)
    implementation(libs.org.springframework.cloud.spring.cloud.config.server)

    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testImplementation(libs.junit.junit)
    testImplementation(libs.org.testcontainers.vault)
}
