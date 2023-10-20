import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm {
        compilations.all {
            compilerOptions.configure {
                jvmTarget.set(JvmTarget.JVM_1_8)
            }
        }
    }

    linuxX64()
    linuxArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.structures)
                implementation(libs.bundles.ktor.client)
            }
        }
    }
}
