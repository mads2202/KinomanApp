package com.mads2202.kinomanapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mads2202.kinomanapp.model.jsonModel.personModel.Person
import com.mads2202.kinomanapp.retrofit.peopleApi.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response


class PersonListViewModel(val personRepository: PersonRepository) : ViewModel() {
    lateinit var persons: Flow<Response<Person>>

    init {
        loadPersons()
    }

    private fun loadPersons() {
        persons = flow {
            for (i in 1..1000) {
                emit(personRepository.getPerson(i))
            }
        }

    }
}