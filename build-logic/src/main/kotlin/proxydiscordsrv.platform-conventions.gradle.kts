import java.util.*

plugins {
    id("proxydiscordsrv.shadow-conventions")
}

tasks {
    shadowJar {
        archiveFileName.set("ProxyDiscordSRV-${project.name.substringAfter("proxydiscordsrv-").replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}-${project.version}.jar")
        destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
    }
}