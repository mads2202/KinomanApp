package com.mads2202.kinomanapp.model.jsonModel.personModel

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("birthday") var birthday : String?,
    @SerializedName("known_for_department") var knownForDepartment : String,
    @SerializedName("deathday") var deathday : String?,
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("gender") var gender : Int,
    @SerializedName("biography") var biography : String,
    @SerializedName("place_of_birth") var placeOfBirth : String?,
    @SerializedName("profile_path") var profilePath : String?,

)