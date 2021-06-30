package com.mads2202.kinomanapp.di.viewModelModules

import com.mads2202.kinomanapp.ui.viewModels.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieViewModelModule= module {
    viewModel { MovieViewModel(get()) }
}