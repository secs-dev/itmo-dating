import org.openapitools.generator.gradle.plugin.tasks.GenerateTask as OpenAPIGenerateTask

plugins {
    id("buildlogic.spring-conventions")
    id("com.github.bjornvester.wsdl2java") version "2.0.2"
}

val projectGroup = group

val matchmaker = "matchmaker"
val matchmakerTitle = matchmaker.replaceFirstChar { it.titlecase() }

val clientGeneratedDir = layout.buildDirectory
    .dir("generated/client/$matchmaker").get().toString()

tasks.register<OpenAPIGenerateTask>("openApiGenerate${matchmakerTitle}Client") {
    generatorName = "kotlin"
    inputSpec = rootProject.layout.projectDirectory.asFile
        .let { "$it/$matchmaker/src/main/resources/static/openapi/api.yml" }
    outputDir = clientGeneratedDir
    packageName = "$projectGroup.$matchmaker.client.generated"
    modelPackage = "$projectGroup.$matchmaker.client.model.generated"
    modelNameSuffix = "Message"
    configOptions = mapOf(
        "library" to "jvm-spring-restclient",
        "useSpringBoot3" to "true",
        "serializationLibrary" to "jackson",
    )
}

val versionCxf = "4.1.0"

wsdl2java {
    wsdlDir = file("$projectDir/src/main/resources/wsdl")
    markGenerated = true
    cxfVersion = versionCxf
}

sourceSets {
    main {
        kotlin {
            srcDir("$clientGeneratedDir/src/main/kotlin")
        }
    }
}

tasks.compileKotlin.configure {
    dependsOn("openApiGenerate${matchmakerTitle}Client")
    dependsOn(tasks.wsdl2java)
}

dependencies {
    api(project(":starter-tls"))

    implementation(libs.org.springframework.boot.spring.boot.starter.web)
    implementation(libs.org.springframework.boot.spring.boot.starter.web.services)

    implementation(libs.com.fasterxml.jackson.module.jackson.module.kotlin)
    implementation(libs.org.jetbrains.kotlin.kotlin.reflect)

    implementation("org.springframework.ws:spring-ws-core:4.0.11")
    implementation("org.apache.cxf:cxf-spring-boot-starter-jaxws:$versionCxf")

    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testImplementation(libs.org.jetbrains.kotlin.kotlin.reflect)
    testImplementation(libs.org.jetbrains.kotlin.kotlin.test.junit5)
    testRuntimeOnly(libs.org.junit.platform.junit.platform.launcher)
}
