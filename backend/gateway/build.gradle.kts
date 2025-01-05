plugins {
    id("buildlogic.spring-conventions")
}

dependencies {
    implementation(libs.org.springframework.boot.spring.boot)
    implementation(libs.org.springframework.cloud.spring.cloud.starter.gateway)

    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testImplementation(libs.junit.junit)
}
