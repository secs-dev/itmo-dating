plugins {
    id("buildlogic.java-library-conventions")
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    java
    application
}

dependencies {
    api(project(":people-server"))
    api(project(":people-database"))
    api(libs.org.springframework.boot.spring.boot.starter.web)
    api(libs.org.springdoc.springdoc.openapi.starter.webmvc.ui)
    testImplementation(project(":common-testing"))
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    compileOnly(libs.org.projectlombok.lombok)
    annotationProcessor(libs.org.projectlombok.lombok)
    testCompileOnly(libs.org.projectlombok.lombok)
    testAnnotationProcessor(libs.org.projectlombok.lombok)
}

application {
    mainClass = "${group}.people.OpenApiGeneratorApplication"
}
