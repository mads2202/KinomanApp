package com.mads2202.kinomanapp.model.roomModel

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "actor", indices = arrayOf(
        Index(
            "actorId",
            unique = true
        )
    )
)
data class Actor(
    @PrimaryKey
    val actorId: Int,
    var name: String,
    var movieID: Int
)