plugins {
    id("buildlogic.spring-conventions")
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
    mainClass = "${group}.people.ApplicationKt"
}
