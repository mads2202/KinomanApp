package com.mads2202.kinomanapp.model.jsonModel.moviesModel


import com.google.gson.annotations.SerializedName


data class Crew(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("job") var job: String?
)