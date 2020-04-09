package com.opus

import android.app.Application
import android.os.StrictMode
import com.opus.di.AppModules
import com.opus.mobile.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupStrictMode()
        setupTimber()
        initKoin()
    }

    private fun initKoin() = startKoin {
        androidContext(this@MyApplication)
        androidLogger()
        modules(AppModules.modules)
    }

    private fun setupTimber() {
        Timber.plant(DebugTree())
    }

    private fun setupStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
    }
}
