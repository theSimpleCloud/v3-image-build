package app.simplecloud.simplecloud.imagebuild.config

/**
 * Date: 18.04.23
 * Time: 11:57
 * @author Frederick Baier
 *
 */
interface BuildConfigWrapper {

    fun getTemplate(name: String): Template

    fun getBuildProfiles(): Map<String, BuildProfile>

}