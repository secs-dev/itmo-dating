import org.openapitools.generator.gradle.plugin.tasks.GenerateTask as OpenAPIGenerateTask

plugins {
    id("buildlogic.kotlin-library-conventions")
    id("org.openapi.generator")
    application
}

extra["generateOAPIServer"] = { serviceName: String ->
    val serviceTitle = serviceName.replaceFirstChar { it.titlecase() }
    val group = group

    val apiResourcesDir = layout.projectDirectory.asFile.let { "$it/src/main/resources" }
    val generatedDir = layout.buildDirectory.dir("generated/server/$serviceName").get().toString()

    val taskName = "openApiGenerate${serviceTitle}Server"
    tasks.register<OpenAPIGenerateTask>(taskName) {
        generatorName = "kotlin-spring"
        inputSpec = "$apiResourcesDir/static/openapi/api.yml"
        outputDir = generatedDir
        invokerPackage = "$group"
        apiPackage = "$group.$serviceName.api.generated"
        modelPackage = "$group.$serviceName.model.generated"
        modelNameSuffix = "Message"
        configOptions = mapOf(
            "delegatePattern" to "true",
            "useSpringBoot3" to "true",
            "reactive" to "true",
            "serializationLibrary" to "jackson"
        )
    }

    sourceSets {
        main {
            kotlin {
                srcDir("$generatedDir/src/main/kotlin")
            }
        }
    }

    tasks.compileKotlin.configure {
        dependsOn(taskName)
    }

    application {
        mainClass = "$group.ApplicationKt"
    }
}
