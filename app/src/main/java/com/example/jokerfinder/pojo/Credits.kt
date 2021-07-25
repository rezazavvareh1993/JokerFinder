package com.example.jokerfinder.pojo

data class Credits(
    val id: Int,
    val cast: List<Cast>? = null,
    val crew: List<Crew>? = null
)