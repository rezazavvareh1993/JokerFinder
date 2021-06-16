package com.example.jokerfinder.pojo

import com.squareup.moshi.JsonClass

data class Credits(
    val id: Int,
    val cast: List<Cast>? = null,
    val crew: List<Crew>? = null
)