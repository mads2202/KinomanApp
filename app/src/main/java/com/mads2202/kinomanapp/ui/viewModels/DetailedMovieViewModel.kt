package com.mads2202.kinomanapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.DetailedMovie
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieParticipantRequest
import com.mads2202.kinomanapp.retrofit.movieApi.MovieRepository
import com.mads2202.kinomanapp.util.networkUtil.Resource
import kotlinx.coroutines.launch

class DetailedMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val detailedMovieLiveData = MutableLiveData<Resource<DetailedMovie>>()
    val movieParticipant = MutableLiveData<Resource<MovieParticipantRequest>>()
    var id: Int = 1

    fun loadDetailedMovie() {

        viewModelScope.launch {
            val response = movieRepository.getDetailedMovieInfo(id)
            detailedMovieLiveData.postValue(Resource.loading(null))
            if (response.isSuccessful) {
                detailedMovieLiveData.postValue(Resource.success(response.body()))
            } else {
                detailedMovieLiveData.postValue(Resource.error(response.message(), null))
            }
        }
        viewModelScope.launch {
            val response = movieRepository.getMovieParticipants(id)
            movieParticipant.postValue(Resource.loading(null))
            if (response.isSuccessful) {
                movieParticipant.postValue(Resource.success(response.body()))
            } else {
                movieParticipant.postValue(Resource.error(response.message(), null))
            }

        }
    }
}