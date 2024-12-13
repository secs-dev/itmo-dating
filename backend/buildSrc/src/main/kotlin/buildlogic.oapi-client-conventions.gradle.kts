import org.openapitools.generator.gradle.plugin.tasks.GenerateTask as OpenAPIGenerateTask

plugins {
    id("buildlogic.kotlin-library-conventions")
    id("org.openapi.generator")
    application
}

extra["generateOAPIClient"] = { serviceName: String ->
    val serviceTitle = serviceName.replaceFirstChar { it.titlecase() }
    val group = group

    val root = rootProject.layout.projectDirectory.asFile.let { "$it/$serviceName" }
    val apiResourcesDir = layout.projectDirectory.asFile.let { "$root/src/main/resources" }
    val generatedDir = layout.buildDirectory.dir("generated/client/$serviceName").get().toString()

    val taskName = "openApiGenerate${serviceTitle}Client"
    tasks.register<OpenAPIGenerateTask>(taskName) {
        generatorName = "kotlin"
        inputSpec = "$apiResourcesDir/static/openapi/api.yml"
        outputDir = generatedDir
        packageName = "$group.$serviceName.client.generated"
        modelPackage = "$group.$serviceName.client.model.generated"
        modelNameSuffix = "Message"
        configOptions = mapOf(
            "library" to "jvm-spring-webclient",
            "useSpringBoot3" to "true",
            "serializationLibrary" to "jackson",
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
}
