plugins {
    kotlin("jvm") version "1.8.0"
    id ("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

group = "app.simplecloud"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.yaml:snakeyaml:1.33")
    implementation("commons-io:commons-io:2.11.0")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("app.simplecloud.simplecloud.imagebuild.MainKt")
}