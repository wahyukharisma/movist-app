package com.example.movist.services.model.movie

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dates(
    val maximum: String,
    val minimum: String
)