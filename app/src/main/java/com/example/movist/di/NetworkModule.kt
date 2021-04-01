package com.example.movist.di

import com.example.movist.BuildConfig
import com.example.movist.presentation.BaseApplication
import com.example.movist.services.network.MovieServices
import com.example.movist.util.Constants
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

    private val okHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(ChuckInterceptor(BaseApplication.appContext))
            .build()

    @Singleton
    @Provides
    fun provideService() : MovieServices{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
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