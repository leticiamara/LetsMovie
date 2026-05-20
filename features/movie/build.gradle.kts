import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

extensions.configure<LibraryExtension> {
    namespace = "com.leticiafernandes.movie"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "MOVIE_IMAGE_BASE_URL", "\"https://image.tmdb.org/t/p/original\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "MOVIE_IMAGE_BASE_URL", "\"https://image.tmdb.org/t/p/original\"")
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    lint {
        targetSdk = 35
    }
    testOptions {
        targetSdk = 35
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Rx
    implementation(libs.rxkotlin)
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.adapter.rxjava3)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Coil
    implementation(libs.coil)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
