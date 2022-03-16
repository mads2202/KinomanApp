package com.mads2202.kinomanapp.ui.viewModels


import androidx.lifecycle.*
import com.mads2202.kinomanapp.model.roomModel.MovieDB
import com.mads2202.kinomanapp.room.repository.MovieRepositoryDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class FavoriteMovieViewModel(private val movieRepositoryDB: MovieRepositoryDB) : ViewModel() {

    val _favoriteMovies = MutableLiveData<List<MovieDB>>()
    val favoriteMovies: LiveData<List<MovieDB>> = _favoriteMovies

    init {
        loadMovieFromDB()
    }

    private fun loadMovieFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteMovies.postValue(movieRepositoryDB.getMovies())
        }
    }

    class FavoriteMovieViewModelFactory @Inject constructor(private val movieRepositoryDB: MovieRepositoryDB) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoriteMovieViewModel(movieRepositoryDB) as T
        }
    }
}