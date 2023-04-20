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

package app.simplecloud.simplecloud.imagebuild

import app.simplecloud.simplecloud.imagebuild.config.BuildConfig
import app.simplecloud.simplecloud.imagebuild.config.BuildConfigWrapperImpl
import app.simplecloud.simplecloud.imagebuild.utils.Downloader
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.io.File
import java.net.MalformedURLException
import java.net.URL

fun main() {
    val yaml = Yaml(Constructor(BuildConfig::class.java))
    val buildConfigPath = System.getenv("BUILD_CONFIG")
    val yamlFile = if (isValidUrl(buildConfigPath)) {
        val tmpFile = File("tmp/buildconfig.yaml")
        Downloader.userAgentDownload(buildConfigPath, tmpFile)
        tmpFile
    } else {
        File(buildConfigPath)
    }

    val yamlFileContent = yamlFile.readText()
    val buildConfig: BuildConfig = yaml.load(yamlFileContent)
    val wrapper = BuildConfigWrapperImpl(buildConfig)

    ImageBuilder(wrapper, System.getenv("BUILDKIT_ADDR")!!, System.getenv("REGISTRY")).buildImages()
}

private fun isValidUrl(url: String): Boolean {
    return try {
        URL(url)
        true
    } catch (e: MalformedURLException) {
        false
    }
}