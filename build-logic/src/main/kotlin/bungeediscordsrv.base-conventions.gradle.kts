plugins {
    `java-library`
    `maven-publish`
    signing
}

tasks {
    // Variable replacements
    processResources {
        filesMatching(listOf("plugin.yml", "bungee.yml")) {
            expand("version" to project.version, "description" to project.description)
        }
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.compilerArgs.addAll(listOf("-nowarn", "-Xlint:-unchecked", "-Xlint:-deprecation"))
    }
    test {
        useJUnitPlatform()
    }
}

java {
    javaTarget(17)
    withSourcesJar()
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

publishing {
    publications.create<MavenPublication>("mavenJava") {
        groupId = rootProject.group as String
        artifactId = project.name
        version = rootProject.version as String
        pom {
            name.set("BungeeDiscordSRV")
            description.set("A plugin to allow proxy servers to communicate with Discord")
            url.set("https://github.com/JamieIsGeek/BungeeDiscordSRV")
            licenses {
                license {
                    name.set("GNU GPLv3")
                    url.set("https://www.gnu.org/licenses/gpl-3.0.html")
                }
            }
            developers {
                developer {
                    id.set("JamieIsGeek")
                    name.set("Jamie")
                }
            }
        }
    }
}