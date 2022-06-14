package com.mads2202.kinomanapp.retrofit.peopleApi

import com.mads2202.kinomanapp.model.jsonModel.personModel.Person
import com.mads2202.kinomanapp.model.jsonModel.personModel.PersonMoviesRequest
import retrofit2.Response
import javax.inject.Inject

class PersonRepository @Inject constructor(private val personApiService: PersonApiService) {
    suspend fun getPerson(id: Int): Response<Person> = personApiService.getPerson(id)
    suspend fun getPersonMovies(id: Int): Response<PersonMoviesRequest> =
        personApiService.getPersonMovies(id)
}