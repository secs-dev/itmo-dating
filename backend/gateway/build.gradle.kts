plugins {
    id("buildlogic.spring-conventions")
}

dependencies {
    implementation(project(":starter-monitoring"))
    implementation(project(":starter-service-discovery"))
    implementation(project(":starter-tls"))

    implementation(libs.org.springframework.boot.spring.boot)
    implementation(libs.org.springframework.cloud.spring.cloud.starter.gateway)
    implementation(libs.org.springframework.spring.web)
    implementation(libs.org.springdoc.springdoc.openapi.starter.webflux.ui)

    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testImplementation(libs.junit.junit)
}
