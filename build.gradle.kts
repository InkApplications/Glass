@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    id("maven-publish")
    id("signing")
}

gradle.startParameter.excludedTaskNames.add("lint")

publishing {
    publications {
        repositories {
            maven {
                name = "MavenCentral"
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = project.properties.getOrDefault("mavenUser", null)?.toString()
                    password = project.properties.getOrDefault("mavenPassword", null)?.toString()
                }
            }
        }
        withType<MavenPublication> {
            pom {
                name.set("GlassConsole ${project.name}")
                description.set("Display server and SDK for arbitrary UI control")
                url.set("https://glass.inkapplications.com")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://choosealicense.com/licenses/mit/")
                    }
                }
                developers {
                    developer {
                        id.set("reneevandervelde")
                        name.set("Renee Vandervelde")
                        email.set("Renee@InkApplications.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/InkApplications/GlassConsole.git")
                    developerConnection.set("scm:git:ssh://git@github.com:InkApplications/GlassConsole.git")
                    url.set("https://github.com/InkApplications/GlassConsole")
                }
            }
        }
    }
}
signing {
    val signingKey = project.properties.getOrDefault("signingKey", null)?.toString()
    val signingKeyId = project.properties.getOrDefault("signingKeyId", null)?.toString()
    val signingPassword = project.properties.getOrDefault("signingPassword", null)?.toString()
    val shouldSign = signingKeyId != null && signingKey != null && signingPassword != null

    if (shouldSign) {
        useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
        sign(publishing.publications)
    }
}
