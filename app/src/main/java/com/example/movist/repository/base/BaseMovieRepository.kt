package com.example.movist.repository.base

interface BaseMovieRepository {
    suspend fun getPopular(token: String)
    suspend fun getNowPlaying(token: String)
    suspend fun getUpcoming(token: String)
    suspend fun getTopRated(token: String)
    suspend fun getDetail(token: String, id: Int)
    suspend fun getReview(token: String, id: Int)
}