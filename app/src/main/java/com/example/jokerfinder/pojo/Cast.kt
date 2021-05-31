package com.example.jokerfinder.pojo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cast(
    val character: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val order: Int,
    @Json(name = "credit_id") val creditId: String,
    @Json(name = "cast_id") val castId: Int,
    @Json(name = "profile_path") val profilePath: String
)