package com.example.movist.di

import android.content.Context
import androidx.room.Room
import com.example.movist.services.storage.LocalDatabase
import com.example.movist.services.storage.dao.MovieFavoriteDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideMovieFavDao(localDatabase : LocalDatabase) : MovieFavoriteDAO {
        return localDatabase.getMovieFavorite()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : LocalDatabase{
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "MovieDB"
        ).build()
    }
}