package com.mads2202.kinomanapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mads2202.kinomanapp.model.jsonModel.personModel.Person
import com.mads2202.kinomanapp.retrofit.peopleApi.PersonRepository
import retrofit2.Response

class ActorsPostDataSource(private val personRepository: PersonRepository) :
    PagingSource<Int, Person>() {
    private val PAGE_SIZE = 8
    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val data = getPersons(currentLoadingPageKey)
            val responseData = mutableListOf<Person>()
            responseData.addAll(data)
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    suspend fun getPersons(page: Int): ArrayList<Person> {
        val persons = ArrayList<Person>()
        for (i in page * PAGE_SIZE - PAGE_SIZE until page * PAGE_SIZE) {
            var response = personRepository.getPerson(i)
            if (response.isSuccessful) {
                response.body()?.let { persons.add(it) }
            }
        }
        return persons
    }
}