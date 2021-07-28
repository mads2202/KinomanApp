package com.mads2202.kinomanapp.model.jsonModel.personModel

data class PersonMovies(
    var id: Int,
    var job: String?,
    var posterPath: String?,
    var title: String,
    var voteAverage: Double,
    var releaseDate: String?,
)