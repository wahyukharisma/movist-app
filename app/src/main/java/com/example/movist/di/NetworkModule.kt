package com.example.movist.di

import com.example.movist.BuildConfig
import com.example.movist.services.network.MovieServices
import com.example.movist.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideService() : MovieServices{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieServices::class.java)
    }

    @Singleton
    @Provides
    @Named("token")
    fun provideAuthToken(): String{
        return "Bearer ${BuildConfig.API_KEY}"
    }
}