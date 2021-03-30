package com.example.movist.di

import com.example.movist.R
import com.example.movist.services.network.MovieServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.themoviedb.org/3"
    private const val API_TOKEN = R.string.token.toString()

    @Singleton
    @Provides
    fun provideService() : MovieServices{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MovieServices::class.java)
    }

    @Singleton
    @Provides
    @Named("token")
    fun provideAuthToken(): String{
        return API_TOKEN
    }
}