plugins {
    id("buildlogic.java-library-conventions")
}

dependencies {
    api(project(":people-server"))
    api(project(":people-database"))
    api(libs.org.springframework.boot.spring.boot.starter.web)
    api(libs.org.springdoc.springdoc.openapi.starter.webmvc.ui)
    testImplementation(project(":common-testing"))
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    compileOnly(libs.org.projectlombok.lombok)
}
