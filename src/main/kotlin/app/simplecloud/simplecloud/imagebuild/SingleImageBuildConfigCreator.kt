package app.simplecloud.simplecloud.imagebuild

import app.simplecloud.simplecloud.imagebuild.config.BuildConfigWrapper
import app.simplecloud.simplecloud.imagebuild.config.BuildProfile
import app.simplecloud.simplecloud.imagebuild.config.TemplateWithName

/**
 * Date: 19.04.23
 * Time: 10:47
 * @author Frederick Baier
 *
 */
class SingleImageBuildConfigCreator(
    private val buildProfileName: String,
    private val buildProfile: BuildProfile,
    private val configWrapper: BuildConfigWrapper,
    private val remoteBuildKitAddr: String,
    private val destRegistry: String,
) {

    fun create(): SingleImageBuildConfig {
        return SingleImageBuildConfig(
            this.buildProfile,
            createOrderedTemplateList(),
            remoteBuildKitAddr,
            "${this.destRegistry}/${buildProfileName.lowercase()}:latest"
        )
    }

    private fun createOrderedTemplateList(): List<TemplateWithName> {
        val resultList = ArrayList<TemplateWithName>()
        val highestTemplateName = this.buildProfile.template
        var template = this.configWrapper.getTemplate(highestTemplateName)
        resultList.add(TemplateWithName(highestTemplateName, template.inheritedTemplate, template.downloadFiles))

        while (template.inheritedTemplate != null) {
            val inheritedTemplate = this.configWrapper.getTemplate(template.inheritedTemplate!!)
            resultList.add(
                TemplateWithName(
                    template.inheritedTemplate!!,
                    inheritedTemplate.inheritedTemplate,
                    inheritedTemplate.downloadFiles
                )
            )
            template = inheritedTemplate
        }
        return resultList.reversed()
    }

}