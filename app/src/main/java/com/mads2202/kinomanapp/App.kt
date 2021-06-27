package com.mads2202.kinomanapp

import android.app.Application
import com.mads2202.kinomanapp.di.appModule
import com.mads2202.kinomanapp.di.repositoryModule
import com.mads2202.kinomanapp.di.viewModelModules.movieViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(listOf(appModule, repositoryModule, movieViewModelModule))
        }
    }

}