package com.example.movist.services.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_favorite")
data class MovieFavorite(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val movieId: Int,
    val title: String,
    val date: String,
    val description: String,
    val image: String
)