package com.example.jokerfinder.pojo

import com.squareup.moshi.Json

data class ResultModel(
    val popularity: Double,
    val video: Boolean,
    val id: Int,
    val adult: Boolean,
    val title: String,
    val overview: String,
    @field:Json(name = "poster_path") val posterPath: String,
    @field:Json(name = "vote_count") val voteCount: Int,
    @field:Json(name = "vote_average") val voteAverage: Double,
    @field:Json(name = "backdrop_path") val backdropPath: String,
    @field:Json(name = "original_language") val originalLanguage: String,
    @field:Json(name = "original_title") val originalTitle: String,
    @field:Json(name = "release_date") val releaseDate: String,
    @field:Json(name = "genre_ids") val genreIds: List<Int>? = null
)
