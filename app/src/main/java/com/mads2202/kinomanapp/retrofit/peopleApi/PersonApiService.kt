package com.mads2202.kinomanapp.retrofit.peopleApi


import com.mads2202.kinomanapp.model.jsonModel.personModel.Person
import com.mads2202.kinomanapp.model.jsonModel.personModel.PersonMoviesRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonApiService {
    @GET("person/{person_id}")
    suspend fun getPerson(@Path("person_id") id: Int): Response<Person>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovies(@Path("person_id") id: Int): Response<PersonMoviesRequest>

}