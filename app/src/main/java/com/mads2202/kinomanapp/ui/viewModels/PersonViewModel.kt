package com.mads2202.kinomanapp.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mads2202.kinomanapp.model.jsonModel.personModel.Person
import com.mads2202.kinomanapp.model.jsonModel.personModel.PersonCrewWork
import com.mads2202.kinomanapp.model.jsonModel.personModel.PersonRoles
import com.mads2202.kinomanapp.retrofit.peopleApi.PersonRepository
import com.mads2202.kinomanapp.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(private val personRepository: PersonRepository) : ViewModel() {
    val _persons = MutableLiveData<Resource<Person>>()
    val persons: LiveData<Resource<Person>> = _persons
    val _roles = MutableLiveData<Resource<List<PersonRoles>>>()
    val roles: LiveData<Resource<List<PersonRoles>>> = _roles
    val _crewsMember = MutableLiveData<Resource<List<PersonCrewWork>>>()
    val crewsMember: LiveData<Resource<List<PersonCrewWork>>> = _crewsMember
    var id: Int = 1

    fun loadPerson() {
        viewModelScope.launch(Dispatchers.IO) {
            _persons.postValue(Resource.loading(null))
            val response = personRepository.getPerson(id)
            if (response.isSuccessful) {
                _persons.postValue(Resource.success(response.body()))
            } else {
                _persons.postValue(Resource.error(response.message(), null))
            }
        }
    }

    fun loadPersonMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _roles.postValue(Resource.loading(null))
            val response = personRepository.getPersonMovies(id)
            if (response.isSuccessful) {
                response.body()?.let { _roles.postValue(Resource.success(it.roles)) }

            } else {
                _roles.postValue(Resource.error(response.message(), null))
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            _crewsMember.postValue(Resource.loading(null))
            val response = personRepository.getPersonMovies(id)
            if (response.isSuccessful) {
                response.body()?.let { _crewsMember.postValue(Resource.success(it.crew)) }
            } else {
                _persons.postValue(Resource.error(response.message(), null))
            }
        }

    }
}