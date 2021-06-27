package com.mads2202.kinomanapp.di

import com.mads2202.kinomanapp.retrofit.movieApi.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepository(get()) }
}