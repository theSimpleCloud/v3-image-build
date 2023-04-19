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

package app.simplecloud.simplecloud.imagebuild.configurator

import app.simplecloud.simplecloud.imagebuild.utils.ConfigurationFileEditor
import java.io.File

/**
 * Date: 19.04.23
 * Time: 10:40
 * @author Frederick Baier
 *
 */
class VelocityProcessConfigurator : ProcessConfigurator {
    override fun configure(tmpDir: File, orderedTemplateDirs: List<File>) {
        copyFile("velocity.toml", tmpDir, orderedTemplateDirs)
        val configFile = File(tmpDir, "velocity.toml")
        val fileEditor = ConfigurationFileEditor(configFile, ConfigurationFileEditor.TOML_SPLITTER)
        fileEditor.setValue("bind", "\"0.0.0.0:25565\"")
        //fileEditor.setValue("show-max-players", "${cloudService.getMaxPlayers()}")
        fileEditor.saveToFile(configFile)
    }
}