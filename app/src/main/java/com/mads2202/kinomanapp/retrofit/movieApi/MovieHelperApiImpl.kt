package com.mads2202.kinomanapp.retrofit.movieApi

import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MoviesRequest
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieType
import retrofit2.Response

class MovieHelperApiImpl(private val apiService: ApiService) : MovieApiHelper {
    override suspend fun getMovies(movieType: MovieType): Response<MoviesRequest>{
        return when(movieType){
            MovieType.POPULAR-> apiService.getPopularMovies(1)
            MovieType.TOPRATED-> apiService.getTopRatedMovies(1)
            MovieType.UPCOMING-> apiService.getUpcomingMovies(1)

        }
    }


}