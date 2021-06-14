package com.example.jokerfinder.pojo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ResultModel(
    val popularity: Double,
    val video: Boolean,
    val id: Int,
    val adult: Boolean,
    val title: String,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "genre_ids") val genreIds: List<Int>? = null
)