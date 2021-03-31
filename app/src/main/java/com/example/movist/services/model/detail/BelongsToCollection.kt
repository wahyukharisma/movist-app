package com.example.movist.services.model.detail

import com.google.gson.annotations.SerializedName

data class BelongsToCollection(
    val backdrop_path: String,
    val id: Int,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String
)