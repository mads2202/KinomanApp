package com.mads2202.kinomanapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mads2202.kinomanapp.model.jsonModel.personModel.Person
import com.mads2202.kinomanapp.model.jsonModel.personModel.PersonCrewWork
import com.mads2202.kinomanapp.model.jsonModel.personModel.PersonMoviesRequest
import com.mads2202.kinomanapp.model.jsonModel.personModel.PersonRoles
import com.mads2202.kinomanapp.retrofit.peopleApi.PersonApiService
import com.mads2202.kinomanapp.retrofit.peopleApi.PersonRepository
import com.mads2202.kinomanapp.util.networkUtil.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PersonViewModel(private val personRepository: PersonRepository) : ViewModel() {
    val persons = MutableLiveData<Resource<Person>>()
    val roles = MutableLiveData<Resource<List<PersonRoles>>>()
    val crewsMember = MutableLiveData<Resource<List<PersonCrewWork>>>()
    var id: Int = 1

    fun loadPerson() {
        viewModelScope.launch(Dispatchers.IO) {
            persons.postValue(Resource.loading(null))
            val response = personRepository.getPerson(id)
            if (response.isSuccessful) {
                persons.postValue(Resource.success(response.body()))
            } else {
                persons.postValue(Resource.error(response.message(), null))
            }
        }
    }

    fun loadPersonMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            roles.postValue(Resource.loading(null))
            val response = personRepository.getPersonMovies(id)
            if (response.isSuccessful) {
                response.body()?.let { roles.postValue(Resource.success(it.roles)) }

            } else {
                roles.postValue(Resource.error(response.message(), null))
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            crewsMember.postValue(Resource.loading(null))
            val response = personRepository.getPersonMovies(id)
            if (response.isSuccessful) {
                response.body()?.let { crewsMember.postValue(Resource.success(it.crew)) }
            } else {
                persons.postValue(Resource.error(response.message(), null))
            }
        }

    }
}

