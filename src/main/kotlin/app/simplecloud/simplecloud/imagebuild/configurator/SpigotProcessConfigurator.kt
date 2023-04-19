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