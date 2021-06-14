package com.example.jokerfinder.pojo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Crew(
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val name: String,
    @Json(name = "credit_id") val creditId: String,
    @Json(name = "profile_path") val profilePath: String
)