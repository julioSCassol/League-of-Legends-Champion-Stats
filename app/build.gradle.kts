plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.league_of_legends_application"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.league_of_legends_application"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // OkHttp for networking
    implementation(libs.okhttp)
    implementation(libs.core)

    // MockWebServer for testing
    testImplementation(libs.mockwebserver)

    // Turbine for testing Kotlin Flow emissions
    testImplementation(libs.turbine)

    // Mockito for mocking
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)

    // Coroutines Test for coroutine-based testing
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)

    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.ui.test.junit4.android)
    testImplementation(libs.androidx.ui.test.junit4.android)

    testImplementation(libs.mockk)

    // Core AndroidX Testing
    androidTestImplementation(libs.androidx.core.testing)

    // Compose dependencies
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.runtime)

    // Core AndroidX dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Firebase dependencies
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging.v2305)
    implementation(libs.google.firebase.analytics)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)

    // JUnit and testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose testing dependencies
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}