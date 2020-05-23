package com.opus.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.opus.data.dataSource.local.LocalDataSource
import com.opus.data.dataSource.remote.FirebaseImpl
import com.opus.data.dataSource.remote.RemoteDataSource
import com.opus.repository.LoginRepositoryImpl
import com.opus.ui.login.LoginViewModel
import com.opus.ui.newOrder.NewOrderFragment
import com.opus.ui.newOrder.NewOrderViewModel
import com.opus.ui.splashScreen.SplashScreenViewModel
import com.opus.util.voiceRecognition.VoiceRecognition
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules  {
    val modules: List<Module> =
        listOf(
            viewModelModule,
            repositoryModule,
            firebaseModule,
            dataSourceModule,
            voiceRecognitionModule
        )
}

private val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SplashScreenViewModel() }
    viewModel { NewOrderViewModel(get()) }
}

private val repositoryModule = module {
    single { LoginRepositoryImpl(get(), get()) }
}

private val dataSourceModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource() }
}

private val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseImpl(get()) }
    single { FirebaseDatabase.getInstance() }
}

private val voiceRecognitionModule = module {
    single { VoiceRecognition(androidApplication()) }
}