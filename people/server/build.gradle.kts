plugins {
    id("buildlogic.java-library-conventions")
}

dependencies {
    api(project(":people-api"))
    api(libs.io.swagger.core.v3.swagger.annotations)
    api(libs.io.swagger.core.v3.swagger.models)
    api(libs.org.openapitools.jackson.databind.nullable)
    api(libs.org.springframework.spring.web)
    api(libs.org.springframework.spring.context)
    api(libs.org.springframework.boot.spring.boot)
    api(libs.org.springframework.boot.spring.boot.starter.web)
    api(libs.javax.validation.validation.api)
    api(libs.javax.annotation.javax.annotation.api)
    api(libs.com.fasterxml.jackson.core.jackson.databind)
    compileOnly(libs.javax.servlet.javax.servlet.api)
    compileOnly(libs.org.projectlombok.lombok)
}
