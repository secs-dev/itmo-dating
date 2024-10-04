plugins {
    id("buildlogic.java-library-conventions")
}

dependencies {
    api(libs.org.testcontainers.postgresql)
    api(libs.junit.junit)
    compileOnly(libs.org.projectlombok.lombok)
}
