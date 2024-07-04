plugins {
    id("library")
    id("published")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.structures)
            implementation(libs.iospin.libsodium)
            implementation(libs.bundles.ktor.client)
            implementation(libs.ktor.client.cio)
            api(libs.ink.regolith.init)
            api(libs.ink.regolith.data)
            api(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
