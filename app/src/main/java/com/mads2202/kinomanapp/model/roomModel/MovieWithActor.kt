package com.mads2202.kinomanapp.model.roomModel

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithActor(
    @Embedded
    val movieDB: MovieDB,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "actorId",
        associateBy = Junction(MovieActorCrossRef::class)
    )
    val actors: List<Actor>
)