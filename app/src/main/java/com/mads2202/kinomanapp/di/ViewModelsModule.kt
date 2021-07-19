package com.mads2202.kinomanapp.di

import com.mads2202.kinomanapp.ui.viewModels.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { DetailedMovieViewModel(get()) }
    viewModel { StartPageViewModel(get()) }
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { PersonViewModel(get()) }
    viewModel { DetailedFavoriteMovieViewModel(get()) }
    viewModel { PersonListViewModel(get()) }
}

