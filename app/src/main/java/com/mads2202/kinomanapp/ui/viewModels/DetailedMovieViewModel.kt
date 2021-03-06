package com.mads2202.kinomanapp.ui.viewModels

import androidx.lifecycle.*
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.DetailedMovie
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieParticipantRequest
import com.mads2202.kinomanapp.retrofit.movieApi.MovieRepository
import com.mads2202.kinomanapp.common.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailedMovieViewModel (private val movieRepository: MovieRepository) : ViewModel() {
    val _detailedMovieLiveData = MutableLiveData<Resource<DetailedMovie>>()
    val detailedMovieLiveData: LiveData<Resource<DetailedMovie>> = _detailedMovieLiveData
    val _movieParticipant = MutableLiveData<Resource<MovieParticipantRequest>>()
    val movieParticipant: LiveData<Resource<MovieParticipantRequest>> = _movieParticipant
    var id: Int = 1

    fun loadDetailedMovie() {
        viewModelScope.launch {
            val response = movieRepository.getDetailedMovieInfo(id)
            _detailedMovieLiveData.postValue(Resource.loading(null))
            if (response.isSuccessful) {
                _detailedMovieLiveData.postValue(Resource.success(response.body()))
            } else {
                _detailedMovieLiveData.postValue(Resource.error(response.message(), null))
            }
        }
        viewModelScope.launch {
            val response = movieRepository.getMovieParticipants(id)
            _movieParticipant.postValue(Resource.loading(null))
            if (response.isSuccessful) {
                _movieParticipant.postValue(Resource.success(response.body()))
            } else {
                _movieParticipant.postValue(Resource.error(response.message(), null))
            }

        }
    }

    class DetailedMovieViewModelFactory@Inject constructor(private val movieRepository: MovieRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailedMovieViewModel(movieRepository) as T
        }
    }
}