package com.example.movist.services.network

import com.example.movist.services.model.detail.Detail
import com.example.movist.services.model.movie.Movie
import com.example.movist.services.model.review.Review
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MovieServices {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Header("Authorization") bearer: String,
    ): Movie

    @GET("movie/popular")
    suspend fun getPopular(
        @Header("Authorization") bearer: String,
    ): Movie

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Header("Authorization") bearer: String,
    ): Movie

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Header("Authorization") bearer: String,
    ): Movie

    @GET("movie/{movie_id}")
    suspend fun getDetail(
        @Header("Authorization") bearer: String,
        @Path("movie_id") movieId: Int,
    ): Detail

    @GET("movie/{movie_id}/reviews")
    suspend fun getReview(
        @Header("Authorization") bearer: String,
        @Path("movie_id") movieId: Int,
    ): Review
}