plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}

rootProject.name = "itmo-dating-backend"

include(
    ":foundation",
    ":foundation-test",
    ":gateway",
    ":config",
    ":authik",
    ":matchmaker",
    ":people",
    ":starter-monitoring",
    ":starter-service-discovery",
    ":starter-tls",
)
include("matchmaker-soap")
