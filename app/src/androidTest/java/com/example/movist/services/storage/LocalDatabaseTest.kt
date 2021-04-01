package com.example.movist.services.storage

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movist.services.storage.dao.MovieFavoriteDAO
import com.example.movist.services.storage.entities.MovieFavorite
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalDatabaseTest : TestCase(){
    private lateinit var db: LocalDatabase
    private lateinit var dao: MovieFavoriteDAO

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java).build()
        dao = db.getMovieFavorite()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun writeAndReadMovie() = runBlocking{
        val movie = MovieFavorite(title = "Batman",
                movieId = 1,date = "2021-03-18",
                description = "This is the last batman series",
                image = "www.google.com")

        dao.insertMovie(movie)
        val readMovie = dao.getMovies()

        assertThat(readMovie.contains(movie)).isTrue()
    }

    @Test
    fun writeAndReadSpecificMovie() = runBlocking{
        val movie = MovieFavorite(title = "Batman",
                movieId = 1,date = "2021-03-18",
                description = "This is the last batman series",
                image = "www.google.com")

        dao.insertMovie(movie)
        val readMovie = dao.getMovie(movieId = movie.movieId)

        assertThat(readMovie.title).isEqualTo(movie.title)
    }

    @Test
    fun writeAndRemoveMovie() = runBlocking{
        val movieBatman = MovieFavorite(title = "Batman",
                movieId = 1,date = "2021-03-18",
                description = "This is the last batman series",
                image = "www.google.com")

        val movieSpiderman = MovieFavorite(title = "Spiderman",
                movieId = 1,date = "2021-03-18",
                description = "This is the last spiderman series",
                image = "www.google.com")

        dao.insertMovie(movieSpiderman)
        dao.insertMovie(movieBatman)
        dao.removeMovie(movieSpiderman)

        val readMovie = dao.getMovies()

        assertThat(readMovie.contains(movieSpiderman)).isFalse()
    }

    @Test
    fun writeAndRemoveAllMovie() = runBlocking{
        val movieBatman = MovieFavorite(title = "Batman",
                movieId = 1,date = "2021-03-18",
                description = "This is the last batman series",
                image = "www.google.com")

        val movieSpiderman = MovieFavorite(title = "Spiderman",
                movieId = 1,date = "2021-03-18",
                description = "This is the last spiderman series",
                image = "www.google.com")

        dao.insertMovie(movieSpiderman)
        dao.insertMovie(movieBatman)
        dao.removeAllMovie()

        val readMovie = dao.getMovies()

        assertThat(readMovie.contains(movieSpiderman)).isFalse()
        assertThat(readMovie.contains(movieBatman)).isFalse()
    }
}