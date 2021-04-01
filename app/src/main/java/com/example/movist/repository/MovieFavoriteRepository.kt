package com.example.movist.repository

import com.example.movist.services.storage.dao.MovieFavoriteDAO
import com.example.movist.services.storage.entities.MovieFavorite
import javax.inject.Inject

class MovieFavoriteRepository(
    private val movieFavDao : MovieFavoriteDAO
    )
{
    suspend fun insertMovie(data : MovieFavorite) = movieFavDao.insertMovie(data)
    suspend fun getMovies() : List<MovieFavorite> = movieFavDao.getMovies()
    suspend fun getMovieById(movieId : Int) : MovieFavorite = movieFavDao.getMovie(movieId)
    suspend fun removeMovie(data : MovieFavorite) = movieFavDao.removeMovie(data)
    suspend fun removeAllMovie() = movieFavDao.removeAllMovie()
}