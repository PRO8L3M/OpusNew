package com.opus.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.opus.data.FirebaseImpl
import com.opus.data.repository.LoginRepository
import com.opus.ui.login.LoginViewModel
import com.opus.ui.home.OrderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules : ModuleLoader() {
    override val modules: List<Module> =
        listOf(
            viewModelModule,
            repositoryModule,
            firebaseModule
        )
}

private val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { OrderViewModel(get()) }
}

private val repositoryModule = module {
    single { LoginRepository(get()) }
}

private val firebaseModule = module {
    single { FirebaseAuth.getInstance() }

    single { FirebaseImpl(get()) }

    single { FirebaseDatabase.getInstance() }
}