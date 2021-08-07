package com.example.jokerfinder.pojo
import com.squareup.moshi.Json

data class BelongsToCollection(
    val id: Int,
    val name: String,
    @field:Json(name = "poster_path")val posterPath: String,
    @field:Json(name = "backdrop_path")val backdropPath: String
)
