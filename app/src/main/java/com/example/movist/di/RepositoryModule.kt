package com.example.movist.di

import com.example.movist.repository.MovieFavoriteRepository
import com.example.movist.repository.MovieRepository
import com.example.movist.repository.base.BaseMovieRepository
import com.example.movist.services.network.MovieServices
import com.example.movist.services.storage.dao.MovieFavoriteDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieServices: MovieServices
    ): MovieRepository{
        return MovieRepository(
            movieServices
        )
    }

    @Singleton
    @Provides
    fun provideMovieFavRepository(
        movieFavDao : MovieFavoriteDAO
    ): MovieFavoriteRepository{
        return MovieFavoriteRepository(
            movieFavDao
        )
    }
}