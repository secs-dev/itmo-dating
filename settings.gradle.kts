rootProject.name = "itmo-dating"

include(
    ":common",
    ":common-testing",
    ":matchmaker",
    ":matchmaker-api",
    ":matchmaker-app",
    ":matchmaker-database",
    ":matchmaker-server",
    ":people",
    ":people-api",
    ":people-app",
    ":people-database",
    ":people-server",
)

project(":matchmaker-api").projectDir = file("matchmaker/api")
project(":matchmaker-app").projectDir = file("matchmaker/app")
project(":people-database").projectDir = file("people/database")
project(":matchmaker-server").projectDir = file("matchmaker/server")
project(":common-testing").projectDir = file("common/testing")
project(":people-app").projectDir = file("people/app")
project(":matchmaker-database").projectDir = file("matchmaker/database")
project(":people-api").projectDir = file("people/api")
project(":people-server").projectDir = file("people/server")
