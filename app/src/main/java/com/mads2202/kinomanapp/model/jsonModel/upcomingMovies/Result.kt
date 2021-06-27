package com.mads2202.kinomanapp.model.jsonModel.upcomingMovies

data class Result(
    var poster_path: String?,
    var adult: Boolean?,
    var overview: String?,
    var release_date: String?,
    var genre_ids: List<Int>?,
    var id: Int?,
    var original_title: String?,
    var original_language: String?,
    var title: String?,
    var backdrop_path: String?,
    var popularity: Double?,
    var vote_count: Int?,
    var video: Boolean?,
    var vote_average: Double?,
)