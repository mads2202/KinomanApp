package com.mads2202.kinomanapp.model.jsonModel.personModel

import com.google.gson.annotations.SerializedName

data class PersonRoles(
    @SerializedName("character") var character: String,
    @SerializedName("release_date") var releaseDate: String?,
    @SerializedName("vote_average") var voteAverage: Double,
    @SerializedName("title") var title: String,
    @SerializedName("id") var id: Int,
    @SerializedName("poster_path") var posterPath: String?
)