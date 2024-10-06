plugins {
    id("buildlogic.spring-conventions")
    id("org.openapi.generator") version "6.3.0"
}

dependencies {
    api(project(":matchmaker-api"))
    api(libs.io.swagger.core.v3.swagger.annotations)
    api(libs.io.swagger.core.v3.swagger.models)
    api(libs.org.openapitools.jackson.databind.nullable)
    api(libs.org.springframework.spring.web)
    api(libs.org.springframework.spring.context)
    api(libs.org.springframework.boot.spring.boot)
    api(libs.org.springframework.boot.spring.boot.starter.web)
    api(libs.com.fasterxml.jackson.core.jackson.databind)
    implementation(libs.jakarta.validation.jakarta.validation.api)
}

val apiResourcesDir = project(":matchmaker-api").layout.projectDirectory.asFile
    .let { "$it/src/main/resources" }

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
