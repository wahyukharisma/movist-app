package com.example.movist.services.storage.dao

import androidx.room.*
import com.example.movist.services.storage.entities.MovieFavorite

@Dao
interface MovieFavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieFavorite: MovieFavorite)

    @Query("SELECT * FROM movie_favorite ORDER BY id DESC")
    suspend fun getMovies() : List<MovieFavorite>

    @Query("SELECT * FROM movie_favorite WHERE id = :movieId")
    suspend fun getMovie(movieId : Int) : MovieFavorite

    @Delete
    suspend fun removeMovie(movie: MovieFavorite)

    @Query("DELETE FROM movie_favorite")
    suspend fun removeAllMovie()
}