package com.example.jokerfinder.pojo

import com.squareup.moshi.Json

data class ResponseSearchMovie(
    val page: Int,
    val results: List<ResultModel>? = null,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "total_pages") val totalPages: Int
)
