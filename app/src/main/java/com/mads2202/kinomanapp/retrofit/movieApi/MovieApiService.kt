package com.mads2202.kinomanapp.retrofit.movieApi

import com.mads2202.kinomanapp.model.jsonModel.moviesModel.DetailedMovie
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieParticipantRequest
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MoviesRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): Response<MoviesRequest>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MoviesRequest>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): Response<MoviesRequest>

    @GET("movie/{movie_id}")
    suspend fun getDetailedMovieInfo(@Path("movie_id") id: Int): Response<DetailedMovie>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieParticipants(@Path("movie_id") id: Int): Response<MovieParticipantRequest>
}