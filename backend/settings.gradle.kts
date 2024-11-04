plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}

rootProject.name = "itmo-dating-backend"

include(
    ":foundation",
    ":foundation-test",
    ":matchmaker",
    ":people",
    ":people-api",
    ":people-app",
    ":people-database",
    ":people-server",
)

project(":people-api").projectDir = file("people/api")
project(":people-app").projectDir = file("people/app")
project(":people-server").projectDir = file("people/server")
project(":people-database").projectDir = file("people/database")
