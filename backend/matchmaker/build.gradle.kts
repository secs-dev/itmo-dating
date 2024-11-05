plugins {
    id("buildlogic.spring-conventions")
    id("org.openapi.generator") version "6.3.0"
    application
}

dependencies {
    implementation(project(":foundation"))
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
