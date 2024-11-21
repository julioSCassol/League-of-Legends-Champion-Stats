plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    if (System.getenv("CI") == null) {
        id("com.google.gms.google-services")
    }
    id("jacoco") // Adiciona o plugin Jacoco para cobertura de testes
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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

tasks.matching { it.name == "processDebugGoogleServices" }.configureEach {
    enabled = System.getenv("CI") == null
}

// Configuração para relatório Jacoco
tasks.register("jacocoTestReport", JacocoReport::class) {
    dependsOn("testDebugUnitTest") // Garante que os testes sejam executados antes

    reports {
        xml.required.set(true) // Relatório XML
        html.required.set(true) // Relatório HTML
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*"
    )
    val debugTree = fileTree(mapOf("dir" to "$buildDir/tmp/kotlin-classes/debug", "excludes" to fileFilter))
    sourceDirectories.setFrom(files("src/main/java"))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(files("$buildDir/jacoco/testDebugUnitTest.exec"))
}

dependencies {
    // Dependências principais
    implementation(libs.okhttp)
    implementation(libs.core)

    // Testes de Unidade
    testImplementation(libs.mockwebserver)
    testImplementation(libs.turbine)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)
    testImplementation("org.robolectric:robolectric:4.10.3")
    testImplementation(libs.androidx.ui.test.junit4.android)
    testImplementation(libs.mockk)
    testImplementation("junit:junit:4.13.2")

    // Testes Instrumentados
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("io.mockk:mockk-android:1.13.4")

    // UI do Compose
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging.v2305)
    implementation(libs.google.firebase.analytics)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)

    // Testes Gerais
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Testes para Compose
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
