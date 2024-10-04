plugins {
    id("buildlogic.java-library-conventions")
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
    api(libs.javax.validation.validation.api)
    api(libs.javax.annotation.javax.annotation.api)
    api(libs.com.fasterxml.jackson.core.jackson.databind)
    compileOnly(libs.javax.servlet.javax.servlet.api)
    compileOnly(libs.org.projectlombok.lombok)
}

val apiResourcesDir = project(":matchmaker-api").layout.projectDirectory.asFile
    .let { "$it/src/main/resources" }

val generatedDir = layout.buildDirectory.dir("generated").get().toString()

openApiGenerate {
    generatorName = "spring"
    inputSpec = "$apiResourcesDir/static/openapi/api.yml"
    outputDir = generatedDir
    invokerPackage = "${group}.matchmaker"
    apiPackage = "${group}.matchmaker.api.generated"
    modelPackage = "${group}.matchmaker.model.generated"
    configOptions = mapOf(
        "delegatePattern" to "true",
    )
}

sourceSets {
    main {
        java {
            srcDir("${generatedDir}/src/main/java")
        }
        resources {
            srcDir(apiResourcesDir)
        }
    }
}


tasks.withType<JavaCompile>().configureEach {
    dependsOn("openApiGenerate")
}
