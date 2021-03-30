package com.example.movist.services.model.review

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorDetails(
    @Json(name = "avatar_path")
    val avatarPath: String,
    val name: String,
    val rating: Any,
    val username: String
)