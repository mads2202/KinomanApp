package com.mads2202.kinomanapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.Movie
import com.mads2202.kinomanapp.paging.PopularMoviePostDataSource
import com.mads2202.kinomanapp.paging.TopRatedPostDataSource
import com.mads2202.kinomanapp.paging.UpcomingMoviePostDataSource
import com.mads2202.kinomanapp.retrofit.movieApi.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartPageViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    init {
        loadMovies()
    }

    lateinit var upcomingMoviesList: Flow<PagingData<Movie>>
    lateinit var popularMoviesList: Flow<PagingData<Movie>>
    lateinit var topRatedMoviesList: Flow<PagingData<Movie>>

    class StartPageViewModelFactory @Inject constructor(private val movieRepository: MovieRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StartPageViewModel(movieRepository) as T
        }
    }


    fun loadMovies() {
        upcomingMoviesList = Pager(PagingConfig(20, maxSize = 40, prefetchDistance = 10)) {
            UpcomingMoviePostDataSource(movieRepository)
        }.flow.cachedIn(viewModelScope)

        popularMoviesList = Pager(PagingConfig(20, maxSize = 40, prefetchDistance = 10)) {
            PopularMoviePostDataSource(movieRepository)
        }.flow.cachedIn(viewModelScope)

        topRatedMoviesList = Pager(PagingConfig(20, maxSize = 40, prefetchDistance = 10)) {
            TopRatedPostDataSource(movieRepository)
        }.flow.cachedIn(viewModelScope)
    }

}