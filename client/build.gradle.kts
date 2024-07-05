plugins {
    kotlin("multiplatform")
    id("published")
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvmToolchain(11)
    jvm()

    macosX64()
    macosArm64()
    iosSimulatorArm64()
    iosX64()

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
