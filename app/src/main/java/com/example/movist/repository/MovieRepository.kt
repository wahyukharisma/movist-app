package com.example.movist.repository

import androidx.lifecycle.MutableLiveData
import com.example.movist.services.model.detail.Detail
import com.example.movist.services.model.movie.Movie
import com.example.movist.services.model.review.Review
import com.example.movist.services.network.MovieServices
import com.example.movist.util.ResultOfNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MovieRepository(
    private val movieServices: MovieServices
) {
    val movies = MutableLiveData<ResultOfNetwork<Movie>>()
    val detail = MutableLiveData<ResultOfNetwork<Detail>>()
    val review = MutableLiveData<ResultOfNetwork<Review>>()
    val loadData = MutableLiveData<Boolean>()

    suspend fun getPopularMovies(token: String){
        loadData.postValue(true)
        withContext(Dispatchers.IO){
            movies.postValue(ResultOfNetwork.Success(
                movieServices.getPopular(token)
            ))
        }
    }

    suspend fun getNowPlayingMovies(token: String){
        withContext(Dispatchers.IO){
            movies.postValue(ResultOfNetwork.Success(
                movieServices.getNowPlaying(token)
            ))
        }
    }

    suspend fun getUpcomingMovies(token: String){
        withContext(Dispatchers.IO){
            movies.postValue(ResultOfNetwork.Success(
                movieServices.getUpcoming(token)
            ))
        }
    }

    suspend fun getTopRatedMovies(token: String){
        withContext(Dispatchers.IO){
            movies.postValue(ResultOfNetwork.Success(
                movieServices.getTopRated(token)
            ))
        }
    }

    suspend fun getDetailMovie(token: String, id: Int){
        withContext(Dispatchers.IO){
            detail.postValue(ResultOfNetwork.Success(
                movieServices.getDetail(token, id)
            ))
        }
    }

    suspend fun getReviewMovie(token: String, id: Int){
        withContext(Dispatchers.IO){
            review.postValue(ResultOfNetwork.Success(
                movieServices.getReview(token, id)
            ))
        }
    }
}