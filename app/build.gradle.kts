
import java.util.Properties


plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.kursnewsapp"
    compileSdk = 34

    val localProperties = Properties().apply {
        load(project.rootProject.file("local.properties").inputStream())
    }

    defaultConfig {
        applicationId = "com.example.kursnewsapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "${localProperties["API_KEY"]}")
        resValue ("string", "API_KEY","${localProperties["API_KEY"]}")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }


}



dependencies {
            implementation (deps.core.ktx)
            implementation (deps.appcompat)
            implementation (deps.material)
            implementation (deps.constraintlayout)
            implementation (deps.navigation.fragment)
            implementation (deps.navigation.ui)

            testImplementation (deps.junit)
            androidTestImplementation (deps.test.junit)
            androidTestImplementation (deps.espresso)

            implementation (deps.room.runtime)
            kapt (deps.room.compiler)
            implementation (deps.room.ktx)

            implementation (deps.coroutines.core)
            implementation (deps.coroutines.android)

            testImplementation (deps.mockwebserver)

            implementation (deps.lifecycle.viewmodel)
            implementation (deps.lifecycle.runtime)

            implementation (deps.retrofit)
            implementation (deps.retrofit.gson)
            implementation (deps.okhttp.logging)

            implementation (deps.glide)
            kapt (deps.glide.compiler)
}

