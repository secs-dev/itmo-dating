plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}

rootProject.name = "itmo-dating-backend"

include(
    ":foundation",
    ":foundation-test",
    ":gateway",
    ":authik",
    ":matchmaker",
    ":people",
    ":starter-service-discovery",
    ":starter-tls",
)
