package com.example.jokerfinder.pojo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ResponseSearchMovie(
    val page: Int,
    val results: List<ResultModel>? = null,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "total_pages") val totalPages: Int
)