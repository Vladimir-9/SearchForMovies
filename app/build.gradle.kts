plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")

    kotlin("plugin.serialization")
    kotlin("plugin.parcelize")
    kotlin("android")
    kotlin("kapt")
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = Config.compileSdk
    buildToolsVersion = Config.buildTools

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSDK
        targetSdk = Config.targetSDK
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
        compose = true
    }

    kapt {
        correctErrorTypes = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.constraint)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.fragmentKtx)
    implementation(Dependencies.Android.material)

    implementation(Dependencies.Test.mockito)
    implementation(Dependencies.Test.mockitoKotlin)
    implementation(Dependencies.Test.junit)
    implementation(Dependencies.Test.androidJunit)

    implementation(Dependencies.Glide.glide)
    kapt(Dependencies.Glide.compiler)

    implementation(Dependencies.RecyclerView.recyclerview)
    implementation(Dependencies.RecyclerView.adapterDelegates)
    implementation(Dependencies.RecyclerView.animators)

    implementation(Dependencies.Lifecycle.liveDataKtx)
    implementation(Dependencies.Lifecycle.runtimeKtx)
    implementation(Dependencies.Lifecycle.viewModelKtx)
    implementation(Dependencies.Lifecycle.viewModelCompose)

    implementation(Dependencies.Navigation.runtime)
    implementation(Dependencies.Navigation.navigationCompose)
    implementation(Dependencies.Navigation.navigationUiKtx)
    implementation(Dependencies.Navigation.navigationFragmentKtx)

    implementation(Dependencies.Moshi.moshi)
    implementation(Dependencies.Moshi.moshiKotlin)
    kapt(Dependencies.Moshi.moshiCodegen)

    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.converterMoshi)

    implementation(Dependencies.OkHttp.okhttp)
    implementation(Dependencies.OkHttp.interceptor)

    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Coroutines.coroutineAndroid)

    implementation(Dependencies.Timber.timber)

    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.ktx)
    kapt(Dependencies.Room.compiler)

    implementation(Dependencies.SpeedDialView.speedDial)

    implementation(Dependencies.LeakCanary.leakCanary)

    implementation(Dependencies.Hilt.hiltAndroid)
    kapt(Dependencies.Hilt.compiler)

    val composeBom = platform(Dependencies.Compose.bom)
    implementation(composeBom)

    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.uiTooling)
    implementation(Dependencies.Compose.uiToolingPreview)
    implementation(Dependencies.Compose.liveData)
    implementation(Dependencies.Compose.material)

    implementation(Dependencies.Compose.activityCompose)
    implementation(Dependencies.Compose.accompanist)

    implementation(Dependencies.Coil.accompanist)
}