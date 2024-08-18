@file:Suppress("UNUSED_EXPRESSION")

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.todo"
    compileSdk = 34

    buildFeatures{
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.example.todo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    // ViewModel utilities for Compose
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    // LiveData
    implementation (libs.lifecycle.livedata.ktx)
    // Annotation processor
    implementation (libs.androidx.lifecycle.compiler)

    implementation (libs.androidx.room.runtime)
    annotationProcessor (libs.androidx.room.compiler)
}