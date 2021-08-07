package com.example.jokerfinder.pojo

import com.squareup.moshi.Json

data class ProductionCountry(
    val name: String,
    @field:Json(name = "iso_3166_1") var iso31661: String
)
