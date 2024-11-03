plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger)
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.omerakkoyun.exoplayersample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.omerakkoyun.exoplayersample"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // navigation
    implementation(libs.androidx.navigation.fragment)

    // retrofit
    implementation(libs.retrofit)
    implementation (libs.converter.gson)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // security
    implementation (libs.androidx.security.crypto)

    // paging
    implementation(libs.androidx.paging.runtime.ktx)

    // coil
    implementation(libs.coil)

    // exoplayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)

    // splash
    implementation (libs.androidx.core.splashscreen)
}

kapt {
    correctErrorTypes = true
}