package com.mads2202.kinomanapp.di

import android.content.Context
import androidx.room.Room
import com.mads2202.kinomanapp.room.AppDB
import com.mads2202.kinomanapp.room.DAO.MovieDao
import com.mads2202.kinomanapp.room.repository.MovieRepositoryDB
import org.koin.dsl.module

val dbModule = module {
    single { provideDB(get()) }
    single { provideMovieDao(get()) }
    single { MovieRepositoryDB(get()) }
}

private fun provideDB(context: Context) =
    Room.databaseBuilder(context, AppDB::class.java, "kinomanDB").fallbackToDestructiveMigration()
        .build()

private fun provideMovieDao(appDB: AppDB) = appDB.movieDao()




