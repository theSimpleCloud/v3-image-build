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

package app.simplecloud.simplecloud.imagebuild.utils

import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException

/**
 * Date: 18.04.23
 * Time: 15:14
 * @author Frederick Baier
 *
 */
object FileCopier {

    fun copyFileOutOfJar(fileDestination: File, filePathToCopy: String) {
        val stream = FileCopier::class.java.classLoader.getResourceAsStream(filePathToCopy)

        val parent = fileDestination.parentFile
        parent?.mkdirs()
        if (File(filePathToCopy).exists()) {
            return
        }
        try {
            fileDestination.createNewFile()
            FileUtils.copyInputStreamToFile(stream, fileDestination)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

    }

}