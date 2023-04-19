package app.simplecloud.simplecloud.imagebuild.config

/**
 * Date: 18.04.23
 * Time: 11:58
 * @author Frederick Baier
 *
 */
class BuildConfigWrapperImpl(
    private val buildConfig: BuildConfig
) : BuildConfigWrapper {

    override fun getTemplate(name: String): Template {
        return this.buildConfig.templates[name] ?: Template(null, emptyList())
    }

    override fun getBuildProfiles(): Map<String, BuildProfile> {
        return this.buildConfig.build
    }

}