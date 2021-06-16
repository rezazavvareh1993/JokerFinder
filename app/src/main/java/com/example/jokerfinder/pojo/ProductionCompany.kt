package com.example.jokerfinder.pojo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ProductionCompany(
    val id: Int,
    val name: String,
    @Json(name = "logo_path")val logoPath: String,
    @Json(name = "origin_country") val originCountry: String
)