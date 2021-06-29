package com.mads2202.kinomanapp.retrofit.movieApi

import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.MoviesRequest
import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.MovieType
import retrofit2.Response

interface MovieApiHelper {
     suspend fun getMovies(movieType: MovieType): Response<MoviesRequest>
}