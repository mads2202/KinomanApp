package com.mads2202.kinomanapp.model.jsonModel.moviesModel

import com.google.gson.annotations.SerializedName

data class MovieParticipantRequest( @SerializedName("id") var id : Int,
                                    @SerializedName("cast") var cast : List<Cast>,
                                    @SerializedName("crew") var crew : List<Crew>)
