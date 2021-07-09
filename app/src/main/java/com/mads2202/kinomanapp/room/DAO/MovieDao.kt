package com.mads2202.kinomanapp.room.DAO

import androidx.room.*
import com.mads2202.kinomanapp.model.roomModel.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getMovies(): List<MovieDB>

    @Query("SELECT * FROM movie WHERE movie.movieId=:id")
    suspend fun getMovieById(id: Int): MovieDB?

    @Insert
    suspend fun addMovie(movie: MovieDB)

    @Update
    suspend fun updateMovie(movie: MovieDB)

    @Insert
    suspend fun addActors(actors: List<Actor>)

    @Insert
    suspend fun addDirector(director: Director)

    @Query("SELECT * FROM director WHERE directorId=:id")
    suspend fun getDirector(id: Int): Director

    @Transaction
    @Query("SELECT * FROM movie WHERE movieId=:id")
    suspend fun getMovieWithActors(id: Int): MovieWithActor

    @Insert
    suspend fun addMovieActorCrossRef(movieActorCrossRef: List<MovieActorCrossRef>)
}