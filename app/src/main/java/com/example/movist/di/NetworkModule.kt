package com.example.movist.di

import com.example.movist.presentation.BaseApplication
import com.example.movist.services.network.MovieServices
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkOGFjMGE3YTQyNTE2MGU3ZmViZDM1MTQyOTM5OWI2MCIsInN1YiI6IjVmOTkzY2UwOWE5ZjlhMDAzNjdkNjU0NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ncp51xkjyZSGqwW5T7JdrkFFNDUqPuKiEHOrmsLN_fw"

    private val okHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(ChuckInterceptor(BaseApplication.appContext))
            .build()

    @Singleton
    @Provides
    fun provideService() : MovieServices{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieServices::class.java)
    }

    @Singleton
    @Provides
    @Named("token")
    fun provideAuthToken(): String{
        return "Bearer $API_TOKEN"
    }

    @Singleton
    @Provides
    @Named("base_image_url")
    fun provideBaseImageUrl(): String{
        return "https://image.tmdb.org/t/p/w500"
    }
}