package com.mads2202.kinomanapp.ui.viewModels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mads2202.kinomanapp.model.roomModel.MovieDB
import com.mads2202.kinomanapp.room.repository.MovieRepositoryDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteMovieViewModel(private val movieRepositoryDB: MovieRepositoryDB) : ViewModel() {

    val favoriteMovies = MutableLiveData<List<MovieDB>>()

    init {
        loadMovieFromDB()
    }

    private fun loadMovieFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovies.postValue(movieRepositoryDB.getMovies())
        }
    }
}