plugins {
    id("buildlogic.spring-conventions")
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
    mainClass = "${group}.matchmaker.ApplicationKt"
}
