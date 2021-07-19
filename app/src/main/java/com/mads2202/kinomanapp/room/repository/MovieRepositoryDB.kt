package com.mads2202.kinomanapp.room.repository

import androidx.room.Query
import androidx.room.Transaction
import com.mads2202.kinomanapp.model.roomModel.*
import com.mads2202.kinomanapp.room.DAO.MovieDao

class MovieRepositoryDB(val movieDao: MovieDao) {
    public suspend fun getMovies(): List<MovieDB> = movieDao.getMovies()
    public suspend fun addMovie(movie: MovieDB) = movieDao.addMovie(movie)
    public suspend fun updateMovie(movie: MovieDB) = movieDao.updateMovie(movie)
    public suspend fun getMovieById(id: Int) = movieDao.getMovieById(id)
    public suspend fun addActors(actors: List<Actor>) = movieDao.addActors(actors)
    public suspend fun addDirector(crew: Director) = movieDao.addDirector(crew)
    public suspend fun getDirector(id: Int): Director = movieDao.getDirector(id)
    public suspend fun getMovieWithActors(id: Int): MovieWithActor = movieDao.getMovieWithActors(id)
    public suspend fun addMovieActorCrossRef(movieActorCrossRef: List<MovieActorCrossRef>) =
        movieDao.addMovieActorCrossRef(movieActorCrossRef)
    public suspend fun deleteMovie(id: Int) = movieDao.deleteMovie(id)
    public suspend fun deleteDirector(movieId:Int) = movieDao.deleteDirector(movieId)
    public suspend fun deleteActor(movieId:Int) = movieDao.deleteActor(movieId)
    public suspend fun deleteMovieActorCrossRef(movieId:Int) = movieDao.deleteMovieActorCrossRef(movieId)
}