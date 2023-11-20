enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.codemc.io/repository/maven-public/")
        maven("https://m2.dv8tion.net/releases")
        mavenCentral()
    }

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

pluginManagement {
    plugins {
        id("net.kyori.blossom") version "2.1.0"
        id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
        id("com.github.johnrengelman.shadow") version "8.1.1"
    }
}

rootProject.name = "bungeediscordsrv-parent"

includeBuild("build-logic")

subproject("bungee")
subproject("common")

setupSubproject("bungeediscordsrv") {
    projectDir = file("universal")
}

fun subproject(name: String) {
    setupSubproject("bungeediscordsrv-$name") {
        projectDir = file(name)
    }
}

inline fun setupSubproject(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}