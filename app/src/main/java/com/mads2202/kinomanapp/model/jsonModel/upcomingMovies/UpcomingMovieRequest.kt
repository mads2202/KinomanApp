package com.mads2202.kinomanapp.model.jsonModel.upcomingMovies

import com.google.gson.annotations.SerializedName

data class UpcomingMovieRequest(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var results: List<UpcomingMovie>,
    @SerializedName("dates") var dates: Dates,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int
) {
}