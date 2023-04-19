package app.simplecloud.simplecloud.imagebuild

import app.simplecloud.simplecloud.imagebuild.config.ApiType
import app.simplecloud.simplecloud.imagebuild.config.BuildProfile
import app.simplecloud.simplecloud.imagebuild.config.LoadPhase
import app.simplecloud.simplecloud.imagebuild.config.TemplateWithName
import app.simplecloud.simplecloud.imagebuild.configurator.ProcessConfigurator
import app.simplecloud.simplecloud.imagebuild.configurator.SpigotProcessConfigurator
import app.simplecloud.simplecloud.imagebuild.configurator.VelocityProcessConfigurator
import app.simplecloud.simplecloud.imagebuild.utils.Downloader
import app.simplecloud.simplecloud.imagebuild.utils.FileCopier
import org.apache.commons.io.FileUtils
import java.io.File

/**
 * Date: 18.04.23
 * Time: 11:56
 * @author Frederick Baier
 *
 */
class SingleImageBuildConfig(
    private val buildProfile: BuildProfile,
    private val orderedTemplates: List<TemplateWithName>,
    private val remoteBuildKitAddr: String,
    private val destImageTag: String
) {

    @Volatile
    private var nextFromImage = this.buildProfile.javaImage
    private val dockerfileFile = File(TMP_DIR, "Dockerfile")

    fun build() {
        prepareDirectory()
        buildImage()
        cleanDirectory()
    }

    private fun cleanDirectory() {
        FileUtils.deleteDirectory(File(TMP_DIR))
    }

    private fun buildImage() {
        val command = "buildctl --addr tcp://${this.remoteBuildKitAddr}:1234 build --frontend=dockerfile.v0 --local context=${TMP_DIR} --local dockerfile=${TMP_DIR} --output type=image,name=${this.destImageTag},push=true,registry.insecure=true"
        Runtime.getRuntime().exec(command).waitFor()
    }

    private fun prepareDirectory() {
        this.orderedTemplates.forEach {
            prepareForTemplate(it)
        }
        prepareExecutableImage()
    }

    private fun prepareExecutableImage() {
        //config files
        //start file and include wget for POST plugins
        val finalDir = File(TMP_DIR, "executable_dir/")
        val serverJarFile = File(finalDir, "server.jar")
        Downloader.userAgentDownload(this.buildProfile.jarUrl, serverJarFile)
        FileCopier.copyFileOutOfJar(File(TMP_DIR, "start.sh"), "start.sh")
        val onStartupPlugins = orderedTemplates.flatMap { it.downloadFiles }
            .filter { it.load == LoadPhase.STARTUP }
        val formattedPlugins = onStartupPlugins.joinToString("|") { it.url }

        dockerfileFile.appendText("FROM $nextFromImage")
        dockerfileFile.appendText("COPY executable_dir/ /img/")
        dockerfileFile.appendText("COPY start.sh /start.sh")
        dockerfileFile.appendText("RUN chmod +x start.sh")
        dockerfileFile.appendText("EXPOSE 25565")
        dockerfileFile.appendText("CMD [\"/start.sh\", \"java|-Dcom.mojang.eula.agree=true|-jar|server.jar\", \"$formattedPlugins\"]\"")

        val processConfigurator = determineProcessConfigurator()
        processConfigurator.configure(finalDir, this.orderedTemplates.map { File(TEMPLATE_DIR, it.name) })
    }

    private fun determineProcessConfigurator(): ProcessConfigurator {
        return when (buildProfile.apiType) {
            ApiType.SPIGOT -> SpigotProcessConfigurator()
            ApiType.VELOCITY -> VelocityProcessConfigurator()
            ApiType.BUNGEECORD -> TODO()
        }
    }

    private fun prepareForTemplate(template: TemplateWithName) {
        val templateDir = File(TEMPLATE_DIR, template.name)
        val tmpTemplateDir = File(TMP_DIR, template.name)
        if (templateDir.exists())
            FileUtils.copyDirectory(templateDir, tmpTemplateDir)

        template.downloadFiles.filter { it.load == LoadPhase.BUILD }
            .forEach { Downloader.userAgentDownload(it.url, File(tmpTemplateDir, it.path)) }
        dockerfileFile.appendText("FROM $nextFromImage AS ${template.name}")
        dockerfileFile.appendText("COPY ${template.name}/ /img/")
        this.nextFromImage = template.name
    }


    companion object {
        const val TMP_DIR = "/tmp_build/"
        const val TEMPLATE_DIR = "templates/"
    }

}