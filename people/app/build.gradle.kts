plugins {
    id("buildlogic.kotlin-library-conventions")
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    application
}

dependencies {
    api(project(":people-server"))
    api(project(":people-database"))
    api(libs.org.springframework.boot.spring.boot.starter.web)
    api(libs.org.springdoc.springdoc.openapi.starter.webmvc.ui)
    testImplementation(project(":common-testing"))
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
}

application {
    mainClass = "${group}.people.OpenApiGeneratorApplication"
}
