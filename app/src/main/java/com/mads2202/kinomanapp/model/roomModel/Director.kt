package com.mads2202.kinomanapp.model.roomModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "director")
data class Director(
    @PrimaryKey
    val directorId: Int,
    var name: String
)

