package com.example.jokerfinder.pojo

import com.squareup.moshi.Json

data class ProductionCompany(
    val id: Int,
    val name: String,
    @field:Json(name = "logo_path")val logoPath: String,
    @field:Json(name = "origin_country") val originCountry: String
)
