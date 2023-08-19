plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlinx-serialization")
}

android {
    namespace = "com.tgyuu.soccerfriends"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.tgyuu.soccerfriends"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation("com.github.cachapa:ExpandableLayout:2.9.2")

    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("com.google.dagger:hilt-android:2.44")
    testImplementation("junit:junit:4.12")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.6.1")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("com.google.dagger:hilt-android-testing:2.44")
    testImplementation("io.mockk:mockk:1.13.5")
    kaptTest("com.google.dagger:hilt-android-compiler:2.44")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("org.hamcrest:hamcrest-library:1.3")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")
    androidTestImplementation("io.mockk:mockk:1.13.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestUtil("androidx.test:orchestrator:1.4.2")

    debugImplementation("androidx.fragment:fragment-testing:1.6.1")
}
kapt {
    correctErrorTypes = true
}
