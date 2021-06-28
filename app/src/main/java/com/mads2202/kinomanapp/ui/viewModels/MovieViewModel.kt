package com.mads2202.kinomanapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.UpcomingMovieRequest

import com.mads2202.kinomanapp.retrofit.movieApi.MovieRepository
import com.mads2202.kinomanapp.util.NetworkHelper
import com.mads2202.kinomanapp.util.Resource
import kotlinx.coroutines.launch

class MovieViewModel(val movieRepository: MovieRepository, val networkHelper: NetworkHelper) :
    ViewModel() {
    companion object {
        const val NO_INTERNET_CONNECTION = "No internet connection"
    }

    val results = MutableLiveData<Resource<UpcomingMovieRequest>>()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            //метод кладёт в liveData Resource со значением loading и без данных
            results.postValue(Resource.loading(null))
            //если есть интернет то
            if (networkHelper.isNetworkConnected()) {
                //пытаемся извлечь данные из сети
                movieRepository.getUpcomingMovies().let {
                    //Если полученный Response удычный
                    if (it.isSuccessful) {
                        //тогда кладём Resource в livedata с положительным ответом
                        // и данные полученные из Response
                        results.postValue(Resource.success(it.body()))
                    } else {
                        results.postValue(Resource.error(it.errorBody().toString(), null))
                    }

                }
            } else {
                results.postValue(Resource.error(NO_INTERNET_CONNECTION, null))
            }
        }
    }

}