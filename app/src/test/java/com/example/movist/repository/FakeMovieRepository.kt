package com.example.movist.repository

import androidx.lifecycle.MutableLiveData
import com.example.movist.repository.base.BaseMovieRepository
import com.example.movist.services.model.detail.Detail
import com.example.movist.services.model.movie.Movie
import com.example.movist.services.model.review.Review
import com.example.movist.util.JsonFileReader
import com.example.movist.util.ResultOfNetwork
import com.google.gson.Gson

class FakeMovieRepository : BaseMovieRepository{
    private val observableMovies = MutableLiveData<ResultOfNetwork<Movie>>()
    private val observableDetail = MutableLiveData<ResultOfNetwork<Detail>>()
    private val observableReview = MutableLiveData<ResultOfNetwork<Review>>()

    private val movie : Movie = Gson().fromJson(JsonFileReader("movie_response.json").content, Movie::class.java)
    private val detail : Detail = Gson().fromJson(JsonFileReader("detail_response.json").content, Detail::class.java)
    private val review : Review = Gson().fromJson(JsonFileReader("review_response.json").content, Review::class.java)

    override suspend fun getPopular(token: String) {
        observableMovies.postValue(ResultOfNetwork.Success(
            movie))
    }

    override suspend fun getNowPlaying(token: String) {
        observableMovies.postValue(ResultOfNetwork.Success(
            movie))
    }

    override suspend fun getUpcoming(token: String) {
        observableMovies.postValue(ResultOfNetwork.Success(
            movie))
    }

    override suspend fun getTopRated(token: String) {
        observableMovies.postValue(ResultOfNetwork.Success(
            movie))
    }

    override suspend fun getDetail(token: String, id: Int) {
        observableDetail.postValue(ResultOfNetwork.Success(
                detail
            ))
    }

    override suspend fun getReview(token: String, id: Int) {
        observableReview.postValue(ResultOfNetwork.Success(
                review
            ))
    }
}