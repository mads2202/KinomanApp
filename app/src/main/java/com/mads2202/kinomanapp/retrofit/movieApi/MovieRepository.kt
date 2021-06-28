package com.mads2202.kinomanapp.retrofit.movieApi

import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.MovieType

class MovieRepository(private val movieApiHelper: MovieApiHelper) {
    suspend fun getUpcomingMovies() = movieApiHelper.getMovies(MovieType.UPCOMING)
    suspend fun getTopRatedMovies() = movieApiHelper.getMovies(MovieType.TOPRATED)
    suspend fun getPopularMovies() = movieApiHelper.getMovies(MovieType.POPULAR)
}