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