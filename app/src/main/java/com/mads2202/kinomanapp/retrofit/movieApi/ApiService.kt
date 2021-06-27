package com.mads2202.kinomanapp.retrofit.movieApi


import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.ResultsRequest
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("movie/popular?api_key=0e1b920b4ec305f7bc0185bf9f16ee60&page=1")
    public suspend fun getUpcomingMovies():Response<List<ResultsRequest>>
}