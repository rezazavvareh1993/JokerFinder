package com.example.jokerfinder.pojo
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class BelongsToCollection (
    val id: Int,
    val name: String,
    @Json(name = "poster_path")val posterPath: String,
    @Json(name = "backdrop_path")val backdropPath: String
)