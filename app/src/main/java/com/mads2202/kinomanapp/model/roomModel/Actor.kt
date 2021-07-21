package com.mads2202.kinomanapp.model.roomModel

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "actor", indices = arrayOf(
        Index(
            "actorId",
        )
    ), primaryKeys = ["actorId", "movieID"]
)
data class Actor(
    val actorId: Int,
    var name: String,
    var movieID: Int
)