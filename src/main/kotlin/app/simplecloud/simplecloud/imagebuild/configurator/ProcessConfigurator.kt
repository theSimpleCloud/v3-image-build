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

import app.simplecloud.simplecloud.imagebuild.utils.FileCopier
import java.io.File
import java.nio.file.Files

/**
 * Date: 18.04.23
 * Time: 15:28
 * @author Frederick Baier
 *
 */
interface ProcessConfigurator {


    fun configure(tmpDir: File, orderedTemplateDirs: List<File>)

    fun copyFile(name: String, tmpDir: File, orderedTemplateDirs: List<File>) {
        val tmpFile = File(tmpDir, name)
        orderedTemplateDirs.forEach {
            val fileInTemplate = File(it, name)
            if (fileInTemplate.exists())
                Files.copy(fileInTemplate.toPath(), tmpFile.toPath())
        }

        if (!tmpFile.exists())
            FileCopier.copyFileOutOfJar(tmpFile, name)
    }

}