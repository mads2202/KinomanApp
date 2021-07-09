package com.mads2202.kinomanapp.retrofit.movieApi

import com.mads2202.kinomanapp.model.jsonModel.moviesModel.DetailedMovie
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieParticipantRequest
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MoviesRequest
import retrofit2.Response


class MovieRepository(private val movieApiService: MovieApiService) {
    public suspend fun getUpcomingMovies(page: Int): Response<MoviesRequest> =
        movieApiService.getUpcomingMovies(page)


    public suspend fun getPopularMovies(page: Int): Response<MoviesRequest> =
        movieApiService.getPopularMovies(page)

    public suspend fun getTopRatedMovies(page: Int): Response<MoviesRequest> =
        movieApiService.getTopRatedMovies(page)

    public suspend fun getDetailedMovieInfo(id: Int): Response<DetailedMovie> =
        movieApiService.getDetailedMovieInfo(id)

    public suspend fun getMovieParticipants(id: Int): Response<MovieParticipantRequest> =
        movieApiService.getMovieParticipants(id)
}