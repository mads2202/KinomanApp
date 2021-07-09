package com.mads2202.kinomanapp.model.roomModel

import androidx.room.Embedded
import androidx.room.Relation

data class DirectorWithMovies(
    @Embedded
    val director: Director,
    @Relation(
        parentColumn = "directorId",
        entityColumn = "movieDirectorId"
    )
    val movies: List<MovieDB>
)