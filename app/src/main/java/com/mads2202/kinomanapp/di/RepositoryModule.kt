package com.mads2202.kinomanapp.di

import com.mads2202.kinomanapp.retrofit.movieApi.MovieRepository
import com.mads2202.kinomanapp.retrofit.peopleApi.PersonRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepository(get()) }
    single { PersonRepository(get()) }
}