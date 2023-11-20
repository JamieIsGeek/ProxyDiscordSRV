import java.util.*

plugins {
    id("bungeediscordsrv.shadow-conventions")
}

tasks {
    shadowJar {
        archiveFileName.set("BungeeDiscordSRV-${project.name.substringAfter("bungeediscordsrv-").replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}-${project.version}.jar")
        destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
    }
}