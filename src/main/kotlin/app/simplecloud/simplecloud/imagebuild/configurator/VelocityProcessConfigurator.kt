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