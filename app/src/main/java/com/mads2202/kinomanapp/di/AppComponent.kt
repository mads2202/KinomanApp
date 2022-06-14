package com.mads2202.kinomanapp.di

import android.content.Context
import androidx.room.Room
import com.mads2202.kinomanapp.retrofit.movieApi.MovieApiService
import com.mads2202.kinomanapp.retrofit.peopleApi.PersonApiService
import com.mads2202.kinomanapp.room.AppDB
import com.mads2202.kinomanapp.room.dao.MovieDao
import com.mads2202.kinomanapp.ui.fragments.*
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DbModule::class])
interface AppComponent {
    fun inject(fragment: StartPageFragment)
    fun inject(fragment: FavoriteMoviesListFragment)
    fun inject(fragment: DetailedMovieFragmentParent)
    fun inject(fragment: DetailedMovieFragmentDB)
    fun inject(fragment: DetailedMovieFragment)
    fun inject(fragment: DetailedActorFragment)
    fun inject(fragment: ActorsListFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder

    }
}

@Module
class NetworkModule() {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()
                    val originalHttpUrl: HttpUrl = original.url
                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", "0e1b920b4ec305f7bc0185bf9f16ee60")
                        .build()

                    // Request customization: add request headers
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .url(url)
                    val request: Request = requestBuilder.build()
                    return chain.proceed(request)
                }
            }).addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

    @Singleton
    @Provides
    fun providePersonApiService(retrofit: Retrofit): PersonApiService =
        retrofit.create(PersonApiService::class.java)
}

@Module
class DbModule() {
    @Singleton
    @Provides
    fun provideDB(context: Context) =
        Room.databaseBuilder(context, AppDB::class.java, "kinomanDB")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideMovieDao(appDB: AppDB): MovieDao = appDB.movieDao()

}