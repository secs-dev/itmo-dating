plugins {
    id("buildlogic.java-conventions")
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    java
    application
}

dependencies {
    implementation(project(":matchmaker-server"))
    implementation(project(":matchmaker-database"))
    implementation(libs.org.springframework.boot.spring.boot.starter.web)
    implementation(libs.org.springdoc.springdoc.openapi.starter.webmvc.ui)
    testImplementation(project(":common-testing"))
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    compileOnly(libs.org.projectlombok.lombok)
    annotationProcessor(libs.org.projectlombok.lombok)
}

application {
    mainClass = "${group}.matchmaker.OpenApiGeneratorApplication"
}
