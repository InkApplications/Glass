@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    id("maven-publish")
}

gradle.startParameter.excludedTaskNames.add("lint")

publishing {
    publications {
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
