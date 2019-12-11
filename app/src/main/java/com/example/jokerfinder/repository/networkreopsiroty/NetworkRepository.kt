package com.example.jokerfinder.repository.networkreopsiroty

import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.retrofit.MovieFinderApi
import io.reactivex.Single

class NetworkRepository (private val movieFinderApi: MovieFinderApi) {

    fun fetchMovieDetails(idMovie: Int, apiKey: String): Single<ResponseDetailMovie> {
        return movieFinderApi.getMovieDetails(idMovie, apiKey)
    }
}