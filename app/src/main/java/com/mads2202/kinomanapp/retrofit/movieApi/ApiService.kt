package com.mads2202.kinomanapp.retrofit.movieApi


import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.DetailedMovie
import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.MoviesRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/upcoming?api_key=0e1b920b4ec305f7bc0185bf9f16ee60")
    public suspend fun getUpcomingMovies(@Query("page") page: Int): Response<MoviesRequest>

    @GET("movie/popular?api_key=0e1b920b4ec305f7bc0185bf9f16ee60")
    public suspend fun getPopularMovies(@Query("page") page: Int): Response<MoviesRequest>

    @GET("movie/top_rated?api_key=0e1b920b4ec305f7bc0185bf9f16ee60")
    public suspend fun getTopRatedMovies(@Query("page") page: Int): Response<MoviesRequest>

    @GET("movie/{movie_id}?api_key=0e1b920b4ec305f7bc0185bf9f16ee60&language=en-US")
    public suspend fun getDetailedMovieInfo(@Path("movie_id") id:Int):Response<DetailedMovie>
}