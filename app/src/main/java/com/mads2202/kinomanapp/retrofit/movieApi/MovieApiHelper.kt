package com.mads2202.kinomanapp.retrofit.movieApi

import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MoviesRequest
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieType
import retrofit2.Response

interface MovieApiHelper {
     suspend fun getMovies(movieType: MovieType): Response<MoviesRequest>
}