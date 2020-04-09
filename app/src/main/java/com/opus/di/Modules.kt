package com.opus.di

import com.opus.data.repository.Repository
import com.opus.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules : ModuleLoader() {
    override val modules: List<Module> =
        listOf(
            viewModelModule,
            repositoryModule
        )
}

private val viewModelModule = module {
    viewModel { LoginViewModel() }
}

private val repositoryModule = module {
    single { Repository() }
}