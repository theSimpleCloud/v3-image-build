/*
 * SimpleCloud is a software for administrating a minecraft server network.
 * Copyright (C) 2023 Frederick Baier & Philipp Eistrach
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
class SingleImageBuilderCreator(
    private val buildProfileName: String,
    private val buildProfile: BuildProfile,
    private val configWrapper: BuildConfigWrapper,
    private val remoteBuildKitAddr: String,
    private val destRegistry: String,
) {

    fun create(): SingleImageBuilder {
        return SingleImageBuilder(
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