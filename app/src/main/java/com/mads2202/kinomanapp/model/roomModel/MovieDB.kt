package com.mads2202.kinomanapp.model.roomModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.Genres
import com.mads2202.kinomanapp.room.Converters

@Entity(tableName = "movie")
data class MovieDB(
    var budget: Int,
    @TypeConverters(Converters::class)
    var genres: List<Genres>,
    @PrimaryKey
    var movieId: Int,
    var overview: String?,
    var posterPath: String?,
    var releaseDate: String,
    var status: String,
    var tagline: String?,
    var title: String,
    var voteAverage: Double,
    val movieDirectorId: Int
)