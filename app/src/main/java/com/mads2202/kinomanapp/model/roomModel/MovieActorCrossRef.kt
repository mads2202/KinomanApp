package com.mads2202.kinomanapp.model.roomModel

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "movieActorCrossRef",
    primaryKeys = ["movieId", "actorId"], indices = arrayOf(
        Index(
            "actorId"
        )
    )
)
data class MovieActorCrossRef(
    val movieId: Int,
    val actorId: Int
)
