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

package app.simplecloud.simplecloud.imagebuild.config

/**
 * Date: 18.04.23
 * Time: 11:44
 * @author Frederick Baier
 *
 */
data class BuildConfig(
    val templates: Map<String, Template>,
    val build: Map<String, BuildProfile>
)

data class Template(
    val inheritedTemplate: String? = null,
    val downloadFiles: List<DownloadFile>
)

data class DownloadFile(
    val url: String,
    val path: String,
    val load: LoadPhase
)

enum class LoadPhase {
    BUILD,
    STARTUP
}

enum class ApiType {
    VELOCITY, BUNGEECORD, SPIGOT;
}

data class BuildProfile(
    val template: String,
    val apiType: ApiType,
    val javaImage: String,
    val jarUrl: String,
    val cmdLineArguments: List<String>,
    val programArguments: List<String>
)