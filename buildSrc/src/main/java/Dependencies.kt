object Dependencies {

    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.6.0"
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val material = "com.google.android.material:material:1.4.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.1.1"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.6"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val mockito = "org.mockito.kotlin:mockito-kotlin:3.2.0"
        const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
        const val androidJunit = "androidx.test.ext:junit:1.1.3"
    }

    object Glide {
        private const val version = "4.14.2"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object RecyclerView {
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
        const val adapterDelegates = "com.hannesdorfmann:adapterdelegates4:4.3.0"
        const val animators = "jp.wasabeef:recyclerview-animators:4.0.2"
    }

    object Lifecycle {
        private const val version = "2.3.1"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"

        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0"
    }

    object Navigation {
        private const val version = "2.3.1"
        const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
        const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        const val runtime = "androidx.hilt:hilt-navigation-compose:1.0.0-rc01"

        const val navigationCompose = "androidx.navigation:navigation-compose:2.4.0-rc01"
    }

    object Moshi {
        private const val version = "1.14.0"
        const val moshi = "com.squareup.moshi:moshi:$version"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$version"
        const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object OkHttp {
        private const val version = "5.0.0-alpha.2"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Coroutines {
        private const val version = "1.5.1-native-mt"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Timber {
        const val timber = "com.jakewharton.timber:timber:4.7.1"
    }

    object Room {
        private const val version = "2.2.5"
        const val runtime = "androidx.room:room-runtime:$version"
        const val ktx = "androidx.room:room-ktx:$version"
        const val compiler = "androidx.room:room-compiler:$version"
    }

    object SpeedDialView {
        const val speedDial = "com.leinardi.android:speed-dial:3.2.0"
    }

    object LeakCanary {
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.7"
    }

    object Hilt {
        private const val version = "2.44.2"
        const val hiltAndroid = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"
    }

    object Compose {
        const val bom = "androidx.compose:compose-bom:2023.01.00"

        const val ui = "androidx.compose.ui:ui"
        const val uiTooling = "androidx.compose.ui:ui-tooling"
        const val material = "androidx.compose.material:material"
        const val liveData = "androidx.compose.runtime:runtime-livedata"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"

        const val accompanist = "com.google.accompanist:accompanist-systemuicontroller:0.30.1"
        const val activityCompose = "androidx.activity:activity-compose:1.7.1"
    }

    object Coil {
        const val accompanist = "io.coil-kt:coil-compose:1.3.1"
    }
}