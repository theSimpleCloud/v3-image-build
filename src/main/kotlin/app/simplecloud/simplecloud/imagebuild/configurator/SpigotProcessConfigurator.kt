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
 * Date: 18.04.23
 * Time: 15:28
 * @author Frederick Baier
 *
 */
class SpigotProcessConfigurator : ProcessConfigurator {

    override fun configure(tmpDir: File, orderedTemplateDirs: List<File>) {
        //copy from other templates
        copyFile("server.properties", tmpDir, orderedTemplateDirs)
        copyFile("bukkit.yml", tmpDir, orderedTemplateDirs)
        copyFile("spigot.yml", tmpDir, orderedTemplateDirs)

        val propertiesFile = File(tmpDir, "server.properties")
        val fileEditor = ConfigurationFileEditor(propertiesFile, ConfigurationFileEditor.PROPERTIES_SPLITTER)
        fileEditor.setValue("server-ip", "0.0.0.0")
        fileEditor.setValue("server-port", "25565")
        //fileEditor.setValue("max-players", cloudService.getMaxPlayers().toString())
        fileEditor.saveToFile(propertiesFile)
    }

}