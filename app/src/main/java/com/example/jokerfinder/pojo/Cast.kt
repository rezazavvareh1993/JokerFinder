package com.example.jokerfinder.pojo

import com.squareup.moshi.Json

data class Cast(
    val character: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val order: Int,
    @field:Json(name = "credit_id") val creditId: String,
    @field:Json(name = "cast_id") val castId: Int,
    @field:Json(name = "profile_path") val profilePath: String
)
