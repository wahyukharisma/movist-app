package com.example.movist.services.model.detail

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Detail(
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "belongs_to_collection")
    val belongsToCollection: BelongsToCollection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @Json(name = "imdb_id")
    val imdbId: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val original_title: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>,
    @Json(name = "release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    @Json(name = "tagline")
    val tagLine: String,
    val title: String,
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
)