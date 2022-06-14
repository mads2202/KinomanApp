package com.mads2202.kinomanapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mads2202.kinomanapp.model.jsonModel.personModel.Person
import com.mads2202.kinomanapp.paging.ActorsPostDataSource
import com.mads2202.kinomanapp.retrofit.peopleApi.PersonRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PersonListViewModel(private val personRepository: PersonRepository) : ViewModel() {
    lateinit var persons: Flow<PagingData<Person>>

    init {
        loadPersons()
    }

    private fun loadPersons() {
        persons = Pager(PagingConfig(5, maxSize = 20, prefetchDistance = 5)) {
            ActorsPostDataSource(personRepository)
        }.flow.cachedIn(viewModelScope)
    }
    class  PersonListViewModelFactory @Inject constructor(private val personRepository: PersonRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return  PersonListViewModel(personRepository) as T
        }
    }
}