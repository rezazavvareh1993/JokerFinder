package com.example.jokerfinder.pojo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class SpokenLanguage(
    val name: String,
    @field:Json(name = "iso_3166_1") var iso31661: String
)