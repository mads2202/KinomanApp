package com.mads2202.kinomanapp.retrofit.movieApi


import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.MovieRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/upcoming?api_key=0e1b920b4ec305f7bc0185bf9f16ee60")
    public suspend fun getUpcomingMovies(@Query("page") page: Int): Response<MovieRequest>

    @GET("movie/popular?api_key=0e1b920b4ec305f7bc0185bf9f16ee60")
    public suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieRequest>

    @GET("movie/top_rated?api_key=0e1b920b4ec305f7bc0185bf9f16ee60")
    public suspend fun getTopRatedMovies(@Query("page") page: Int): Response<MovieRequest>
}