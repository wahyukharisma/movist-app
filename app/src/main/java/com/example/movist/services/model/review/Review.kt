package com.example.movist.services.model.review

import com.google.gson.annotations.SerializedName

data class Review(
    val id: Int,
    val page: Int,
    val results: List<Result>,
    @SerializedName( "total_pages")
    val totalPages: Int,
    @SerializedName( "total_results")
    val totalResults: Int
)