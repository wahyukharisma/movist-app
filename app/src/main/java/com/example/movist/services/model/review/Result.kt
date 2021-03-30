package com.example.movist.services.model.review

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    val author: String,
    @Json(name = "author_details")
    val authorDetails: AuthorDetails,
    val content: String,
    @Json(name = "created_at")
    val createdAt: String,
    val id: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    val url: String
)