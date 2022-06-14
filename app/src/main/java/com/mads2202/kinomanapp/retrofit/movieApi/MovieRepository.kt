package com.mads2202.kinomanapp.retrofit.movieApi

import com.mads2202.kinomanapp.model.jsonModel.moviesModel.DetailedMovie
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieParticipantRequest
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MoviesRequest
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApiService: MovieApiService) {
    suspend fun getUpcomingMovies(page: Int): Response<MoviesRequest> =
        movieApiService.getUpcomingMovies(page)


    suspend fun getPopularMovies(page: Int): Response<MoviesRequest> =
        movieApiService.getPopularMovies(page)

    suspend fun getTopRatedMovies(page: Int): Response<MoviesRequest> =
        movieApiService.getTopRatedMovies(page)

    suspend fun getDetailedMovieInfo(id: Int): Response<DetailedMovie> =
        movieApiService.getDetailedMovieInfo(id)

    suspend fun getMovieParticipants(id: Int): Response<MovieParticipantRequest> =
        movieApiService.getMovieParticipants(id)
}