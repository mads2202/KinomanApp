package com.mads2202.kinomanapp.retrofit.movieApi

class MovieRepository(private val apiService: ApiService) {
    suspend fun getUpcomingMovies() = apiService.getUpcomingMovies()
}