plugins {
    base
    id("proxydiscordsrv.build-logic")
}

allprojects {
    group = "uk.jamieisgeek.proxydiscordsrv"
    version = property("projectVersion") as String
    description = "A proxy version of DiscordSRV"
}

val platforms = setOf(
    projects.proxydiscordsrvBungee,
).map { it.dependencyProject }

val special = setOf(
    projects.proxydiscordsrv
).map { it.dependencyProject }

subprojects {
    when (this) {
        in platforms -> apply(plugin = "proxydiscordsrv.platform-conventions")
        in special -> apply(plugin = "proxydiscordsrv.base-conventions")
        else -> apply(plugin = "proxydiscordsrv.standard-conventions")
    }
}