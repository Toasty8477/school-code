/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("de.mannodermaus.android-junit5")
}
android {
    namespace = "com.example.swe2710project"
    compileSdk = 36

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    defaultConfig {
        applicationId = "com.example.swe2710project"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation("androidx.wear:wear-ongoing:1.1.0")
    implementation("androidx.core:core:1.17.0")

    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.wear.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.material.icons.extended.android)
    implementation(libs.androidx.compose.material3)
    implementation(libs.datastore.preferences)
    implementation(libs.wear.compose.navigation)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.material.icons.extended)
    implementation(libs.androidx.benchmark.common)
    implementation(libs.androidx.junit)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.20.0")
    testImplementation("androidx.test:core:1.7.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.robolectric:robolectric:4.16")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:6.0.0")

    // Health Services
    implementation("androidx.health:health-services-client:1.1.0-alpha05")
    testImplementation("androidx.test:core:1.7.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.robolectric:robolectric:4.16")

    implementation(libs.guava)
    implementation(libs.concurrent.futures)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // To use CallbackToFutureAdapter
    implementation("androidx.concurrent:concurrent-futures:1.3.0")

    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.6.0")

    // Icons?
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.wear.compose:compose-material:1.5.1")

    implementation(libs.horologist.composables)

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")

    // Mockito for mocking objects in tests
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.mockito:mockito-inline:5.2.0") // For mocking final classes/methods

    // For testing LiveData and other Architecture Components
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    testImplementation("org.robolectric:robolectric:4.12.1")

    // --- Architecture Components & Coroutines Testing ---
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0") // <-- ADD THIS LINE

    // Health Services
    implementation("androidx.health:health-services-client:1.1.0-alpha05")

}