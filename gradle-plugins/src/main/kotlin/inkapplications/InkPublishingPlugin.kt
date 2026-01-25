package inkapplications

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar
import org.gradle.plugins.signing.Sign
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository

class InkPublishingPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        pluginManager.apply("maven-publish")
        pluginManager.apply("signing")
        pluginManager.apply("org.jetbrains.dokka")

        val dokkaTask = tasks.named("dokkaGenerate")

        val javadocJar = tasks.register("javadocJar", Jar::class.java) {
            dependsOn(dokkaTask)
            archiveClassifier.set("javadoc")
            from(layout.buildDirectory.dir("dokka/html"))
        }

        extensions.configure(org.gradle.api.publish.PublishingExtension::class.java) {
            repositories {
                val user = findProperty("mavenUser") as String?
                val pass = findProperty("mavenPassword") as String?
                if (user != null && pass != null) {
                    maven {
                        name = "MavenCentral"
                        setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                        credentials {
                            username = user
                            password = pass
                        }
                    }
                }
            }
            publications.withType(MavenPublication::class.java).configureEach {
                artifact(javadocJar)
                pom {
                    name.set("GlassConsole ${project.name}")
                    description.set("Remote Display Controller")
                    url.set("https://glass.inkapplications.com")
                    licenses {
                        license {
                            name.set("GPL-3.0")
                            url.set("https://www.gnu.org/licenses/gpl-3.0.txt")
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

        extensions.configure(org.gradle.plugins.signing.SigningExtension::class.java) {
            val key = findProperty("signingKey") as String?
            val keyId = findProperty("signingKeyId") as String?
            val password = findProperty("signingPassword") as String?
            if (key != null && keyId != null && password != null) {
                useInMemoryPgpKeys(keyId, key, password)
                sign(extensions.getByType(org.gradle.api.publish.PublishingExtension::class.java).publications)
            }
        }

        val signingTasks = tasks.withType(Sign::class.java)
        tasks.withType(PublishToMavenRepository::class.java).configureEach {
            mustRunAfter(signingTasks)
        }
    }
}
