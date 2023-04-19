package app.simplecloud.simplecloud.imagebuild

import app.simplecloud.simplecloud.imagebuild.config.BuildConfig
import app.simplecloud.simplecloud.imagebuild.config.BuildConfigWrapperImpl
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.io.File

fun main() {
    val yaml = Yaml(Constructor(BuildConfig::class.java))
    val filePath = System.getenv("BUILD_CONFIG_PATH")
    val yamlFile = File(filePath).readText()
    val buildConfig: BuildConfig = yaml.load(yamlFile)
    val wrapper = BuildConfigWrapperImpl(buildConfig)

    ImageBuilder(wrapper, System.getenv("BUILDKIT_ADDR")!!, System.getenv("REGISTRY")).buildImages()
}