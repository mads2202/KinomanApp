package com.mads2202.kinomanapp.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.DetailedMovie

@Database(entities = arrayOf(DetailedMovie::class), version = 1)
abstract class AppDB:RoomDatabase() {
}