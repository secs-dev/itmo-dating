plugins {
    id("buildlogic.java-library-conventions")
}

dependencies {
    api(libs.org.springframework.boot.spring.boot.starter.jdbc)
    api(libs.org.liquibase.liquibase.core)
    api(libs.org.postgresql.postgresql)
}
