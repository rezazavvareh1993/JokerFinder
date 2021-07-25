package com.example.jokerfinder.repository.networkreopsitory.retrofit

import com.example.jokerfinder.pojo.Credits
import com.example.jokerfinder.pojo.ResponseDetailMovie
import com.example.jokerfinder.pojo.ResponseSearchMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface JokerFinderApiService {

    @GET("search/movie")
    suspend fun getMovieSearched(@Query("api_key") apiKey : String, @Query("query") query: String, @Query("page") page : Int) : ResponseSearchMovie

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id")movieId : Int, @Query("api_key") apiKey : String) :  ResponseDetailMovie

    @GET("movie/{movie_id}/credits")
    suspend fun getCastsOfMovie(@Path("movie_id")movieId: Int, @Query("api_key") apiKey: String) : Credits
}
