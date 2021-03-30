package com.example.movist.services.model.detail

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(
    val id: Int,
    val name: String
)