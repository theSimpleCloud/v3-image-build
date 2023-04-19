package app.simplecloud.simplecloud.imagebuild

import app.simplecloud.simplecloud.imagebuild.config.BuildConfigWrapper

/**
 * Date: 18.04.23
 * Time: 12:04
 * @author Frederick Baier
 *
 */
class ImageBuilder(
    private val buildConfig: BuildConfigWrapper,
    private val buildKitAddr: String,
    private val registry: String,
) {

    fun buildImages() {
        createImageBuildConfigs().forEach { it.build() }
    }

    private fun createImageBuildConfigs(): List<SingleImageBuildConfig> {
        return this.buildConfig.getBuildProfiles().map {
            SingleImageBuildConfigCreator(it.key, it.value, buildConfig, this.buildKitAddr, this.registry).create()
        }
    }

}