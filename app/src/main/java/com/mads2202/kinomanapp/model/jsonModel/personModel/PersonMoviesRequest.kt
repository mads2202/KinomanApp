package com.mads2202.kinomanapp.model.jsonModel.personModel

import com.google.gson.annotations.SerializedName


data class PersonMoviesRequest(
    @SerializedName("cast")
    var roles: List<PersonRoles>,
    @SerializedName("crew")
    var crew: List<PersonCrewWork>,
    @SerializedName("id")
    var id: Int
)