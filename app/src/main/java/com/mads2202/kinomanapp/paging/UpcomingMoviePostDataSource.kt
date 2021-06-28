package com.mads2202.kinomanapp.paging

import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.Movie
import com.mads2202.kinomanapp.retrofit.movieApi.ApiService
import java.lang.Exception

class UpcomingMoviePostDataSource(private val apiService: ApiService) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val currentPage = params.key ?: 1
            val response = apiService.getUpcomingMovies(currentPage)
            val responseData= mutableListOf<Movie>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)
            val privKey= if( currentPage==1) null else currentPage-1
           return LoadResult.Page(responseData,privKey,currentPage+1)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}