package com.mads2202.kinomanapp.room.repository

import com.mads2202.kinomanapp.model.roomModel.*
import com.mads2202.kinomanapp.room.DAO.MovieDao

class MovieRepositoryDB(private val movieDao: MovieDao) {
    suspend fun getMovies(): List<MovieDB> = movieDao.getMovies()
    suspend fun addMovie(movie: MovieDB) = movieDao.addMovie(movie)
    suspend fun updateMovie(movie: MovieDB) = movieDao.updateMovie(movie)
    suspend fun getMovieById(id: Int) = movieDao.getMovieById(id)
    suspend fun addActors(actors: List<Actor>) = movieDao.addActors(actors)
    suspend fun addDirector(crew: Director) = movieDao.addDirector(crew)
    suspend fun getDirector(id: Int): Director = movieDao.getDirector(id)
    suspend fun getMovieWithActors(id: Int): MovieWithActor = movieDao.getMovieWithActors(id)
    suspend fun addMovieActorCrossRef(movieActorCrossRef: List<MovieActorCrossRef>) =
        movieDao.addMovieActorCrossRef(movieActorCrossRef)

    suspend fun deleteMovie(id: Int) = movieDao.deleteMovie(id)
    suspend fun deleteDirector(movieId: Int) = movieDao.deleteDirector(movieId)
    suspend fun deleteActor(movieId: Int) = movieDao.deleteActor(movieId)
    suspend fun deleteMovieActorCrossRef(movieId: Int) =
        movieDao.deleteMovieActorCrossRef(movieId)
}