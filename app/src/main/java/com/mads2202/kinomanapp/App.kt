package com.mads2202.kinomanapp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.mads2202.kinomanapp.di.*


class App : Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().context(this).build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}