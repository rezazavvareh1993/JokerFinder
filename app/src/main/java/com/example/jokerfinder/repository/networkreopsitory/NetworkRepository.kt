package com.example.jokerfinder.repository.networkreopsitory

import com.example.jokerfinder.repository.networkreopsitory.retrofit.JokerFinderApiService
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val retrofitService: JokerFinderApiService) {

    suspend fun fetchMovieDetails(idMovie: Int, apiKey: String) =
        retrofitService.getMovieDetails(idMovie, apiKey)

    suspend fun fetchCastsOfMovie(idMovie: Int, apiKey: String) =
        retrofitService.getCastsOfMovie(idMovie, apiKey)

    suspend fun fetchMovieSearchData(movieName: String, page: Int, apiKey: String) =
        retrofitService.getMovieSearched(apiKey, movieName, page)
}