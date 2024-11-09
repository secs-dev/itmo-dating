plugins {
    id("buildlogic.kotlin-library-conventions")
    id("org.openapi.generator")
    application
}

extra["generateOAPIServer"] = { serviceName: String ->
    val apiResourcesDir = layout.projectDirectory.asFile.let { "$it/src/main/resources" }
    val generatedDir = layout.buildDirectory.dir("generated").get().toString()

    openApiGenerate {
        generatorName = "kotlin-spring"
        inputSpec = "$apiResourcesDir/static/openapi/api.yml"
        outputDir = generatedDir
        invokerPackage = "$group.$serviceName"
        apiPackage = "$group.$serviceName.api.generated"
        modelPackage = "$group.$serviceName.model.generated"
        configOptions = mapOf(
            "delegatePattern" to "true",
            "useSpringBoot3" to "true",
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
        dependsOn("openApiGenerate")
    }

    application {
        mainClass = "$group.$serviceName.ApplicationKt"
    }
}
