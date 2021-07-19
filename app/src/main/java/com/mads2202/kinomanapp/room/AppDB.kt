package com.mads2202.kinomanapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mads2202.kinomanapp.room.DAO.MovieDao
import com.mads2202.kinomanapp.model.roomModel.Actor
import com.mads2202.kinomanapp.model.roomModel.Director
import com.mads2202.kinomanapp.model.roomModel.MovieActorCrossRef
import com.mads2202.kinomanapp.model.roomModel.MovieDB
import com.mads2202.kinomanapp.util.dbUtil.Converters


@Database(
    entities = arrayOf(
        MovieDB::class,
        Director::class,
        Actor::class,
        MovieActorCrossRef::class
    ), version = 7
)
@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}