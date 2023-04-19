package app.simplecloud.simplecloud.imagebuild.config

/**
 * Date: 18.04.23
 * Time: 11:44
 * @author Frederick Baier
 *
 */
data class BuildConfig(
    val templates: Map<String, Template>,
    val build: Map<String, BuildProfile>
)

data class Template(
    val inheritedTemplate: String? = null,
    val downloadFiles: List<DownloadFile>
)

data class DownloadFile(
    val url: String,
    val path: String,
    val load: LoadPhase
)

enum class LoadPhase {
    BUILD,
    STARTUP
}

enum class ApiType {
    VELOCITY, BUNGEECORD, SPIGOT;
}

data class BuildProfile(
    val template: String,
    val apiType: ApiType,
    val javaImage: String,
    val jarUrl: String,
    val cmdLineArguments: List<String>,
    val programArguments: List<String>
)