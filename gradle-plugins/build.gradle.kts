plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("kotlinMavenPublish") {
            id = "com.inkapplications.gradle.publications"
            implementationClass = "inkapplications.InkPublishingPlugin"
        }
    }
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.dokka.gradle)
}
