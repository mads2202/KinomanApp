package com.mads2202.kinomanapp.model.roomModel

import androidx.room.Entity

@Entity(tableName = "director", primaryKeys = ["directorId", "movieId"])
data class Director(
    val directorId: Int,
    var name: String,
    var movieId: Int
)