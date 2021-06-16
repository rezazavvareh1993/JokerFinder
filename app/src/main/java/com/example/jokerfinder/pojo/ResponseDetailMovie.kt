package com.example.jokerfinder.pojo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ResponseDetailMovie(
    val adult: Boolean,
    val budget: Int,
    val homepage: String,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val genres: List<Genre>? = null,
    @Json(name = "imdb_id") val imdbId: String,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "belongs_to_collection") val belongsToCollection: BelongsToCollection,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguage>? = null,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompany>? = null,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountry>? = null
)