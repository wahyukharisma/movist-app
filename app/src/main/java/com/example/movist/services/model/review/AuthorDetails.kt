package com.example.movist.services.model.review

import com.google.gson.annotations.SerializedName

data class AuthorDetails(
    @SerializedName("avatar_path")
    val avatarPath: String,
    val name: String,
    val rating: Any,
    val username: String
)