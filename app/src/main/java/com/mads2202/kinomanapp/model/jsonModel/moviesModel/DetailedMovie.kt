package com.mads2202.kinomanapp.model.jsonModel.moviesModel

import com.google.gson.annotations.SerializedName

data class DetailedMovie (
    @SerializedName("budget") var budget : Int,
    @SerializedName("genres") var genres : List<Genres>,
    @SerializedName("id") var id : Int,
    @SerializedName("imdb_id") var imdbId : String,
    @SerializedName("overview") var overview : String,
    @SerializedName("poster_path") var posterPath : String,
    @SerializedName("release_date") var releaseDate : String,
    @SerializedName("revenue") var revenue : Int,
    @SerializedName("runtime") var runtime : Int,
    @SerializedName("status") var status : String,
    @SerializedName("tagline") var tagline : String,
    @SerializedName("title") var title : String,
    @SerializedName("vote_average") var voteAverage : Double,
    @SerializedName("vote_count") var voteCount : Int

)
