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
            FileCopier.copyFileOutOfJar(tmpFile, "/${name}")
    }

}