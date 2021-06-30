package com.mads2202.kinomanapp.model.jsonModel.moviesModel

import com.google.gson.annotations.SerializedName

data class MoviesRequest(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var results: List<Movie>,
    @SerializedName("dates") var dates: Dates,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int
) {
}