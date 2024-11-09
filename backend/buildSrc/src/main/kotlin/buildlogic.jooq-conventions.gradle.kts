plugins {
    id("buildlogic.kotlin-library-conventions")
    id("org.jooq.jooq-codegen-gradle")
}

extra["generateJOOQ"] = { serviceName: String ->
    val jooqVersion = "3.19.15"
    val testContainersVersion = "1.20.3"

    val generatedDir = "$projectDir/build/generated"
    val jooqGeneratedDir = "$generatedDir/jooq/src/main/kotlin"

    dependencies {
        jooqCodegen("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")
        jooqCodegen("org.jooq:jooq-meta-extensions:$jooqVersion")
        jooqCodegen("org.jooq:jooq-meta-kotlin:$jooqVersion")
        jooqCodegen("org.postgresql:postgresql:42.7.4")
        jooqCodegen("org.testcontainers:postgresql:$testContainersVersion")
        jooqCodegen("org.testcontainers:testcontainers:$testContainersVersion")
    }

    sourceSets {
        main {
            java {
                srcDir(jooqGeneratedDir)
            }
        }
    }

    jooq {
        executions {
            create("main") {
                val jdbcUrl = {
                    val schemaSql = "$projectDir/src/main/resources/database/changelog.sql"
                    val protocol = "jdbc:tc:postgresql:16"
                    val tmpfs = "TC_TMPFS=/testtmpfs:rw&amp"
                    val script = "TC_INITSCRIPT=file:$schemaSql"
                    "$protocol:///test?$tmpfs;$script"
                }

                configuration {
                    logging = org.jooq.meta.jaxb.Logging.DEBUG
                    jdbc {
                        driver = "org.testcontainers.jdbc.ContainerDatabaseDriver"
                        url = jdbcUrl()
                        username = "postgres"
                        password = "postgres"
                    }
                    generator {
                        name = "org.jooq.codegen.KotlinGenerator"
                        database {
                            name = "org.jooq.meta.postgres.PostgresDatabase"
                            inputSchema = serviceName
                            includes = ".*"
                        }
                        generate {
                            isImmutablePojos = true
                            isPojosAsKotlinDataClasses = true
                            isKotlinNotNullPojoAttributes = true
                            isKotlinNotNullRecordAttributes = true
                            isImplicitJoinPathsToMany = false
                        }
                        target {
                            directory = jooqGeneratedDir
                        }
                    }
                }
            }
        }
    }

    tasks.compileKotlin.configure {
        dependsOn(tasks.jooqCodegen)
    }
}
