plugins {
    id("buildlogic.kotlin-library-conventions")
    kotlin("plugin.spring")
}

dependencies {
    api(libs.org.springframework.spring.context)
    api(libs.org.springframework.spring.web)
    api(libs.org.springframework.spring.webflux)
    implementation("io.netty:netty-handler:4.1.117.Final")
    implementation("io.projectreactor.netty:reactor-netty:1.2.2")
}
