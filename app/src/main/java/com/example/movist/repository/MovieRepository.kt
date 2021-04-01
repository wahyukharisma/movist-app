package com.example.movist.repository

import androidx.lifecycle.MutableLiveData
import com.example.movist.repository.base.BaseMovieRepository
import com.example.movist.services.model.detail.Detail
import com.example.movist.services.model.movie.Movie
import com.example.movist.services.model.review.Review
import com.example.movist.services.network.MovieServices
import com.example.movist.util.ResultOfNetwork

class MovieRepository(
    private val movieServices: MovieServices
) : BaseMovieRepository{
    val movies = MutableLiveData<ResultOfNetwork<Movie>>()
    val detail = MutableLiveData<ResultOfNetwork<Detail>>()
    val review = MutableLiveData<ResultOfNetwork<Review>>()
    val loadData = MutableLiveData<Boolean>()

    override suspend fun getPopular(token: String) {
        loadData.postValue(true)
        movies.postValue(ResultOfNetwork.Success(
            movieServices.getPopular(token)
        ))
    }

    override suspend fun getNowPlaying(token: String) {
        movies.postValue(ResultOfNetwork.Success(
            movieServices.getNowPlaying(token)
        ))
    }

    override suspend fun getUpcoming(token: String) {
        movies.postValue(ResultOfNetwork.Success(
            movieServices.getUpcoming(token)
        ))
    }

    override suspend fun getTopRated(token: String) {
        movies.postValue(ResultOfNetwork.Success(
            movieServices.getTopRated(token)
        ))
    }

    override suspend fun getDetail(token: String, id: Int) {
        detail.postValue(ResultOfNetwork.Success(
            movieServices.getDetail(token, id)
        ))
    }

    override suspend fun getReview(token: String, id: Int) {
        review.postValue(ResultOfNetwork.Success(
            movieServices.getReview(token, id)
        ))
    }
}