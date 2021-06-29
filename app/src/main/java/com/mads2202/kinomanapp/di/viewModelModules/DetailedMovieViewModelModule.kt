package com.mads2202.kinomanapp.di.viewModelModules

import com.mads2202.kinomanapp.ui.viewModels.DetailedMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailedMovieViewModule = module {
    viewModel { DetailedMovieViewModel(get(), get()) }
}

