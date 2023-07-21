plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("com.apollographql.apollo3").version("3.8.2")
}

apollo {
    service("service") {
        packageName.set("com.example")
    }
}


android {
    namespace = "com.example.rmp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.rmp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            resValue("string", "app_name", "RMP-Release")
            isMinifyEnabled = true
            isDebuggable = false
            signingConfig = null
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
        getByName("debug") {
            resValue("string", "app_name", "RMP-Debug")
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

}
dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.apollographql.apollo3:apollo-runtime:3.8.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation ("io.insert-koin:koin-android:3.3.2")
    implementation ("com.squareup.picasso:picasso:2.5.2")
    api ("androidx.core:core-splashscreen:1.0.1")
    api ("io.insert-koin:koin-core:3.3.2")
    api ("io.insert-koin:koin-android:3.3.2")
    api ("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    api ("com.google.code.gson:gson:2.9.1")

    testImplementation("junit:junit:4.13.2")

    testImplementation ("androidx.test:core:1.5.0")
    testImplementation ("org.mockito:mockito-core:4.7.0")
    androidTestImplementation ("org.mockito:mockito-android:4.7.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("com.apollographql.apollo3:apollo-mockserver:3.8.2")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.robolectric:robolectric:4.9")
    testImplementation ("org.assertj:assertj-core:3.23.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
}

