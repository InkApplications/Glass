plugins {
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val dokkaGenerate by tasks.getting

dokka {
    dokkaPublications.html {
        outputDirectory.set(layout.buildDirectory.dir("dokkaOutput"))
    }
}

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokkaGenerate)
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("dokkaOutput"))
}

publishing {
    repositories {
        val mavenUser = findProperty("mavenUser")?.toString()
        val mavenPassword = findProperty("mavenPassword")?.toString()
        if (mavenUser != null && mavenPassword != null) {
            maven {
                name = "MavenCentral"
                setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = mavenUser
                    password = mavenPassword
                }
            }
        }
    }
    publications {
        withType<MavenPublication> {
            artifact(javadocJar)
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

signing {
    val signingKey = findProperty("signingKey")?.toString()
    val signingKeyId = findProperty("signingKeyId")?.toString()
    val signingPassword = findProperty("signingPassword")?.toString()
    val shouldSign = signingKeyId != null && signingKey != null && signingPassword != null

    if (shouldSign) {
        useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
        sign(publishing.publications)
    }
}

val signingTasks: TaskCollection<Sign> = tasks.withType<Sign>()
tasks.withType<PublishToMavenRepository>().configureEach {
    mustRunAfter(signingTasks)
}
