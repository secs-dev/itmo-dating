plugins {
    id("buildlogic.spring-conventions")
}

dependencies {
    implementation(libs.org.springframework.boot.spring.boot)
    implementation(libs.org.springframework.cloud.spring.cloud.starter.gateway)

    implementation(libs.org.springdoc.springdoc.openapi.starter.webflux.ui)

    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testImplementation(libs.junit.junit)
}
