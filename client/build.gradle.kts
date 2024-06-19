plugins {
    id("library")
    id("published")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.structures)
                implementation(libs.bundles.ktor.client)
                implementation(libs.ktor.client.cio)
            }
        }
    }
}
