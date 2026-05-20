import com.android.build.api.dsl.ApplicationExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
}

extensions.configure<ApplicationExtension> {
    namespace = "com.leticiafernandes.letsmovie"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.leticiafernandes.letsmovie"
        minSdk = 23
        targetSdk = 36
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":features:movie"))
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // Rx
    implementation(libs.rxkotlin)
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.adapter.rxjava3)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    // Picasso
    implementation(libs.picasso)

    // Room
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)

    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Navigation
    api(libs.androidx.navigation.fragment.ktx)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.androidx.navigation.dynamic.features.fragment)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockito.core)
    androidTestImplementation(libs.androidx.annotation)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.espresso.contrib) {
        exclude(group = "com.android.support", module = "appcompat-v7")
        exclude(group = "com.android.support", module = "support-v4")
        exclude(group = "com.android.support", module = "design")
        exclude(group = "com.android.support", module = "recyclerview-v7")
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
