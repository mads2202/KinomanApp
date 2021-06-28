package com.mads2202.kinomanapp.retrofit.movieApi

import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.UpcomingMovie
import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.UpcomingMovieRequest
import retrofit2.Response

class MovieHelperApiImpl(private val apiService: ApiService) : MovieApiHelper {
    override suspend fun getUsers(): Response<UpcomingMovieRequest> = apiService.getUpcomingMovies()


}