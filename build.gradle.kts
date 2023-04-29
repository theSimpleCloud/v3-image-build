/*
 * SimpleCloud is a software for administrating a minecraft server network.
 * Copyright (C) 2023 Frederick Baier & Philipp Eistrach
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

plugins {
    kotlin("jvm") version "1.8.0"
    id ("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

group = "app.simplecloud"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.thesimplecloud.eu/artifactory/list/gradle-release-local/")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.yaml:snakeyaml:2.0")
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