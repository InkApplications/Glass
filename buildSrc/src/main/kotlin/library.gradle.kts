plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

kotlin {
    jvmToolchain(11)
    jvm()
}


project.extensions.configure(PublishingExtension::class.java) {
    publications {
        withType<MavenPublication> {
            pom {
                name.set("GlassConsole ${project.name}")
                description.set("Remote Display Controller for Android")
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
