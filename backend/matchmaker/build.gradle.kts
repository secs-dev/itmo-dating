plugins {
    id("buildlogic.spring-conventions")
    id("org.openapi.generator") version "6.3.0"
    application
}

dependencies {
    implementation(libs.io.swagger.core.v3.swagger.annotations)
    implementation(libs.io.swagger.core.v3.swagger.models)
    implementation(libs.org.openapitools.jackson.databind.nullable)
    implementation(libs.org.springdoc.springdoc.openapi.starter.webmvc.ui)

    implementation(libs.org.springframework.spring.web)
    implementation(libs.org.springframework.spring.context)

    implementation(libs.org.springframework.boot.spring.boot)
    implementation(libs.org.springframework.boot.spring.boot.starter.web)

    implementation(libs.org.springframework.boot.spring.boot.starter.jdbc)
    implementation(libs.org.liquibase.liquibase.core)
    implementation(libs.org.postgresql.postgresql)

    implementation(libs.com.fasterxml.jackson.core.jackson.databind)
    implementation(libs.jakarta.validation.jakarta.validation.api)

    testImplementation(project(":foundation-test"))
}

val apiResourcesDir = layout.projectDirectory.asFile.let { "$it/src/main/resources" }
val generatedDir = layout.buildDirectory.dir("generated").get().toString()

openApiGenerate {
    generatorName = "kotlin-spring"
    inputSpec = "$apiResourcesDir/static/openapi/api.yml"
    outputDir = generatedDir
    invokerPackage = "${group}.matchmaker"
    apiPackage = "${group}.matchmaker.api.generated"
    modelPackage = "${group}.matchmaker.model.generated"
    configOptions = mapOf(
        "delegatePattern" to "true",
        "useSpringBoot3" to "true",
    )
}

sourceSets {
    main {
        kotlin {
            srcDir("${generatedDir}/src/main/kotlin")
        }
    }
}

tasks.compileKotlin.configure {
    dependsOn("openApiGenerate")
}

application {
    mainClass = "${group}.matchmaker.ApplicationKt"
}
