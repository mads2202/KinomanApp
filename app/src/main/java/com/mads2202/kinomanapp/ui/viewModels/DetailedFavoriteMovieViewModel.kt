package com.mads2202.kinomanapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mads2202.kinomanapp.model.roomModel.Director
import com.mads2202.kinomanapp.model.roomModel.MovieDB
import com.mads2202.kinomanapp.model.roomModel.MovieWithActor
import com.mads2202.kinomanapp.room.repository.MovieRepositoryDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailedFavoriteMovieViewModel(private val movieRepositoryDB: MovieRepositoryDB) :
    ViewModel() {

    var movieId = 1
    val detailedMovie = MutableLiveData<MovieDB>()
    val actors = MutableLiveData<MovieWithActor>()
    val director = MutableLiveData<Director>()

    fun loadDirector(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            director.postValue(movieRepositoryDB.getDirector(id))
        }
    }

    fun loadActors() {
        viewModelScope.launch(Dispatchers.IO) {
            actors.postValue(movieRepositoryDB.getMovieWithActors(movieId))
        }
    }

    fun loadMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            detailedMovie.postValue(movieRepositoryDB.getMovieById(movieId))
        }
    }
}
