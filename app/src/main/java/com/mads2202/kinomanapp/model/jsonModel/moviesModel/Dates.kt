package com.mads2202.kinomanapp.model.jsonModel.moviesModel

import com.google.gson.annotations.SerializedName

data class Dates (

    @SerializedName("maximum") var maximum : String,
    @SerializedName("minimum") var minimum : String

)
