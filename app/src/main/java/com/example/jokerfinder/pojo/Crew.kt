package com.example.jokerfinder.pojo

import com.squareup.moshi.Json

data class Crew(
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val name: String,
    @field:Json(name = "credit_id") val creditId: String,
    @field:Json(name = "profile_path") val profilePath: String
)
