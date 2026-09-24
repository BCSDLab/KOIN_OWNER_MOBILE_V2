import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.metro)
    alias(libs.plugins.aboutLibraries)
}

aboutLibraries {
    offlineMode = true
    export {
        outputFile = file("../core/designsystem/src/commonMain/composeResources/files/aboutlibraries.json")
        prettyPrint = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}
dependencies {
    implementation(project(":core:di"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:navigation"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature:event"))
    implementation(project(":feature:home"))
    implementation(project(":feature:menu"))
    implementation(project(":feature:order"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:signin"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:store"))
    implementation(project(":shared"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.filekit.dialogs)
    implementation(libs.metrox.viewmodel.compose)

    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)
}

android {
    namespace = "in.koreatech.business"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "in.koreatech.business"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            manifestPlaceholders["appName"] = "@string/app_name"
        }

        debug {
            applicationIdSuffix = ".dev"
            manifestPlaceholders["appName"] = "@string/app_name_dev"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}
