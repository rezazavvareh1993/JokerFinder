package com.example.jokerfinder.pojo

import com.squareup.moshi.Json

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
    @field:Json(name = "imdb_id") val imdbId: String,
    @field:Json(name = "original_language") val originalLanguage: String,
    @field:Json(name = "original_title") val originalTitle: String,
    @field:Json(name = "backdrop_path") val backdropPath: String,
    @field:Json(name = "belongs_to_collection") val belongsToCollection: BelongsToCollection,
    @field:Json(name = "poster_path") val posterPath: String,
    @field:Json(name = "release_date") val releaseDate: String?,
    @field:Json(name = "vote_average") val voteAverage: Double,
    @field:Json(name = "vote_count") val voteCount: Int,
    @field:Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguage>? = null,
    @field:Json(name = "production_companies") val productionCompanies: List<ProductionCompany>? = null,
    @field:Json(name = "production_countries") val productionCountries: List<ProductionCountry>? = null
)
