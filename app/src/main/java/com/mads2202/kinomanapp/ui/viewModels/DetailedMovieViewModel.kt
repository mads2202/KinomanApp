package com.mads2202.kinomanapp.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.DetailedMovie
import com.mads2202.kinomanapp.retrofit.movieApi.ApiService
import com.mads2202.kinomanapp.util.Resource
import kotlinx.coroutines.launch

class DetailedMovieViewModel(private val apiService: ApiService, val id: Int) : ViewModel() {
    val detailedMovieLiveData = MutableLiveData<Resource<DetailedMovie>>()

    init {
        loadDetailedMovie()
    }

    private fun loadDetailedMovie() {
        viewModelScope.launch {
            val response = apiService.getDetailedMovieInfo(id)
            detailedMovieLiveData.postValue(Resource.loading(null))
            if (response.isSuccessful) {
                detailedMovieLiveData.postValue(Resource.success(response.body()))
            } else {
                detailedMovieLiveData.postValue(Resource.error(response.message(), null))
            }
        }
    }
}