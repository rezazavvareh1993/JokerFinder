package com.example.jokerfinder.pojo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ProductionCountry(
    val name: String,
    @Json(name = "iso_3166_1") var iso31661: String
)