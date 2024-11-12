plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}

rootProject.name = "itmo-dating-backend"

include(
    ":foundation",
    ":foundation-test",
    ":authik",
    ":matchmaker",
    ":people",
)
