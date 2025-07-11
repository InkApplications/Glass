plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.inkapplications.glassconsole"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.inkapplications.glassconsole"
        minSdk = 21
        targetSdk = 35
        versionCode = project.properties.getOrDefault("versionCode", "1").toString().toInt()
        versionName = project.properties.getOrDefault("versionName", "SNAPSHOT").toString()
        buildConfigField("String", "COMMIT", project.properties.getOrDefault("commit", null)?.let { "\"$it\"" } ?: "null")

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        create("parameterSigning") {
            storeFile = project.properties.getOrDefault("signingFile", null)
                ?.toString()
                ?.let { File("${project.rootDir}/$it") }
            keyAlias = project.properties.getOrDefault("signingAlias", null)?.toString()
            keyPassword = project.properties.getOrDefault("signingKeyPassword", null)?.toString()
            storePassword = project.properties.getOrDefault("signingStorePassword", null)?.toString()
        }
    }
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            resValue("string", "app_name", "Glass*")
        }
        release {
            resValue("string", "app_name", "Glass")
            isMinifyEnabled = false
            applicationIdSuffix = if (project.properties.getOrDefault("snapshot", "false") == "true") ".snapshot" else null
            signingConfig = if (project.hasProperty("signingFile")) {
                signingConfigs.getByName("parameterSigning")
            } else {
                signingConfigs.getByName("debug")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,INDEX.LIST}"
            excludes += "/META-INF/io.netty.versions.properties"
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugaring)
    implementation(libs.bundles.androidx)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(compose.components.resources)

    implementation(projects.structures)
    implementation(projects.client)
    implementation(libs.bundles.regolith)
    implementation(libs.ink.ui.render.compose)
    implementation(libs.ink.ui.render.remote)

    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.client)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.kimchi.core)
}
