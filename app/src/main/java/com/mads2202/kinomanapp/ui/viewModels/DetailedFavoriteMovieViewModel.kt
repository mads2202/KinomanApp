package com.mads2202.kinomanapp.ui.viewModels

import androidx.lifecycle.*
import com.mads2202.kinomanapp.model.roomModel.Director
import com.mads2202.kinomanapp.model.roomModel.MovieDB
import com.mads2202.kinomanapp.model.roomModel.MovieWithActor
import com.mads2202.kinomanapp.room.repository.MovieRepositoryDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailedFavoriteMovieViewModel(private val movieRepositoryDB: MovieRepositoryDB) :
    ViewModel() {

    var movieId = 1
    val _detailedMovie = MutableLiveData<MovieDB>()
    val detailedMovie: LiveData<MovieDB> = _detailedMovie
    val _actors = MutableLiveData<MovieWithActor>()
    val actors: LiveData<MovieWithActor> = _actors
    val _director = MutableLiveData<Director>()
    val director: LiveData<Director> = _director

    fun loadDirector(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _director.postValue(movieRepositoryDB.getDirector(id))
        }
    }

    fun loadActors() {
        viewModelScope.launch(Dispatchers.IO) {
            _actors.postValue(movieRepositoryDB.getMovieWithActors(movieId))
        }
    }

    fun loadMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            _detailedMovie.postValue(movieRepositoryDB.getMovieById(movieId))
        }
    }

    class DetailedFavoriteMovieViewModelFactory @Inject constructor(private val movieRepositoryDB: MovieRepositoryDB) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailedFavoriteMovieViewModel(movieRepositoryDB) as T
        }
    }
}
