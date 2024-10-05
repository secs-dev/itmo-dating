plugins {
    `java-base`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

group = "ru.ifmo.se.dating"
version = "1.0.0"

java.sourceCompatibility = JavaVersion.VERSION_20

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
