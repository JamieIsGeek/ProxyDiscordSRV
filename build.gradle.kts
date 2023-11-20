plugins {
    base
    id("bungeediscordsrv.build-logic")
}

allprojects {
    group = "uk.jamieisgeek.bungeediscordsrv"
    version = property("projectVersion") as String
    description = "A proxy version of DiscordSRV"
}

val platforms = setOf(
    projects.bungeediscordsrvBungee,
).map { it.dependencyProject }

val special = setOf(
    projects.bungeediscordsrv
).map { it.dependencyProject }

subprojects {
    when (this) {
        in platforms -> apply(plugin = "bungeediscordsrv.platform-conventions")
        in special -> apply(plugin = "bungeediscordsrv.base-conventions")
        else -> apply(plugin = "bungeediscordsrv.standard-conventions")
    }
}