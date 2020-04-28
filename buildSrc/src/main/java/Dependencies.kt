@file:Suppress("MayBeConstant")

object Releases {
    val versionCode = 1
    val versionName = "1.0"
}

object Versions {
    val kotlin = "1.3.61"

    val minSDK = 21
    val targetSDK = 29
    val compileSDK = 29

    val buildToolsVersion = "29.0.2"

    val navigation = "2.3.0-alpha01"
    val appCompat = "1.1.0"
    val material = "1.2.0-alpha06"
    val core_ktx = "1.2.0"
    val constraint = "1.1.3"

    val junit = "4.12"
    val ext_junit = "1.1.1"
    val espresso = "3.2.0"

    val leakCanary = "2.0"

    val koin = "2.1.0-beta-1"

    val timber = "4.7.1"

    val ktlint = "0.29.0"

    val coroutines = "1.3.3"

    val androidxLifecycle = "2.2.0"

    val legacy = "1.0.0"

    val recyclerView = "1.1.0"

    val coroutinesTest = "1.3.1"

    val coreTesting = "2.1.0"
    val okhttpMock = "4.1.0"
    val mockito = "2.23.0"
    val testRunner = "1.2.0"

    val firebase = "17.2.3"
    val firebaseDatabase = "19.2.1"
    val firebaseAuth = "19.3.0"
}

object Libraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val coreKtx = "androidx.core:core-ktx:${Versions.core_ktx}"
    val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"

    val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"

    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidxLifecycle}"
    val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidxLifecycle}"
    val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycle}"
    val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.androidxLifecycle}"
    val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycle}"

    val junit = "junit:junit:${Versions.junit}"
    val extJunit = "androidx.test.ext:junit:${Versions.ext_junit}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val navigationKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    val material = "com.google.android.material:material:${Versions.material}"

    val koin = "org.koin:koin-core:${Versions.koin}"
    val koinExt = "org.koin:koin-core-ext:${Versions.koin}"
    val koinAndroidxExt= "org.koin:koin-androidx-ext:${Versions.koin}"
    val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    val koinAndroidxScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    val koinAndroidxViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    val ktlint = "com.github.shyiko:ktlint:${Versions.ktlint}"

    val androidxLegacy = "androidx.legacy:legacy-support-v4:${Versions.legacy}"

    val coroutiensTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    val okhttpMock = "com.squareup.okhttp3:mockwebserver:${Versions.okhttpMock}"
    val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
    val testRunner = "androidx.test:runner:${Versions.testRunner}"

    val firebase = "com.google.firebase:firebase-analytics:${Versions.firebase}"
    val firebaseDatabase = "com.google.firebase:firebase-database-ktx:${Versions.firebaseDatabase}"
    val firebaseAuth = "com.google.firebase:firebase-auth:${Versions.firebaseAuth}"
}
