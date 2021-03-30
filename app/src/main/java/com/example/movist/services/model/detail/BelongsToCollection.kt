package com.example.movist.services.model.detail

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BelongsToCollection(
    val backdrop_path: String,
    val id: Int,
    val name: String,
    @Json(name = "poster_path")
    val posterPath: String
)