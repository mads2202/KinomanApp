package com.mads2202.kinomanapp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.mads2202.kinomanapp.di.appModule
import com.mads2202.kinomanapp.di.dbModule
import com.mads2202.kinomanapp.di.repositoryModule
import com.mads2202.kinomanapp.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule, repositoryModule, viewModelsModule, dbModule
                )
            )
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}