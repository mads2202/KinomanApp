package com.mads2202.kinomanapp.model.jsonModel.personModel

import com.google.gson.annotations.SerializedName

data class PersonCrewWork(
    @SerializedName("id") var id: Int,
    @SerializedName("job") var job: String,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("title") var title: String,
    @SerializedName("vote_average") var voteAverage: Double,
    @SerializedName("release_date") var releaseDate: String?,
)