package app.simplecloud.simplecloud.imagebuild.config

/**
 * Date: 18.04.23
 * Time: 12:36
 * @author Frederick Baier
 *
 */
class TemplateWithName(
    val name: String,
    val inheritedTemplate: String? = null,
    val downloadFiles: List<DownloadFile>,
) {
}