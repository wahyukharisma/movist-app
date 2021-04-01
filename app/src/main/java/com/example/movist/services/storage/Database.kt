package com.example.movist.services.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movist.services.storage.dao.MovieFavoriteDAO
import com.example.movist.services.storage.entities.MovieFavorite

@Database(
    entities = [
        MovieFavorite::class
    ],
    version = 1
)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun getMovieFavorite() : MovieFavoriteDAO
}