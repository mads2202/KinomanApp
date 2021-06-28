package com.mads2202.kinomanapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mads2202.kinomanapp.paging.PopularMoviePostDataSource
import com.mads2202.kinomanapp.paging.TopRatedPostDataSource
import com.mads2202.kinomanapp.paging.UpcomingMoviePostDataSource
import com.mads2202.kinomanapp.retrofit.movieApi.ApiService

class MovieViewModel(val apiService: ApiService) :
    ViewModel() {
    companion object {
        const val NO_INTERNET_CONNECTION = "No internet connection"
    }
    val upcomingMovieList= Pager(PagingConfig(10)){
        UpcomingMoviePostDataSource(apiService)
    }.flow.cachedIn(viewModelScope)
    val popularMovieList= Pager(PagingConfig(10)){
        PopularMoviePostDataSource(apiService)
    }.flow.cachedIn(viewModelScope)
    val topRatedMovieList= Pager(PagingConfig(10)){
        TopRatedPostDataSource(apiService)
    }.flow.cachedIn(viewModelScope)



}