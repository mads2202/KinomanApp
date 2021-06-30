package com.mads2202.kinomanapp.model.jsonModel.moviesModel

import com.google.gson.annotations.SerializedName

data class Genres (

    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String

)
