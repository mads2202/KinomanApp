package com.mads2202.kinomanapp.di

import android.content.Context
import com.mads2202.kinomanapp.retrofit.movieApi.ApiService
import com.mads2202.kinomanapp.retrofit.movieApi.MovieApiHelper
import com.mads2202.kinomanapp.retrofit.movieApi.MovieHelperApiImpl
import com.mads2202.kinomanapp.util.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.BuildConfig


import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"

val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), BASE_URL) }
    single { provideApiService(get()) }
    single{ provideApiHelperImp(get())}
    single { provideNetworkHelper(androidContext()) }
}

private fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()
}

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)
private fun provideNetworkHelper(context: Context) = NetworkHelper(context)
private fun provideApiHelperImp(apiService: ApiService):MovieApiHelper=MovieHelperApiImpl(apiService)