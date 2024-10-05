plugins {
    id("buildlogic.kotlin-conventions")
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    application
}

dependencies {
    implementation(project(":matchmaker-server"))
    implementation(project(":matchmaker-database"))
    implementation(libs.org.springframework.boot.spring.boot.starter.web)
    implementation(libs.org.springdoc.springdoc.openapi.starter.webmvc.ui)
    testImplementation(project(":common-testing"))
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
}

application {
    mainClass = "${group}.matchmaker.OpenApiGeneratorApplication"
}
