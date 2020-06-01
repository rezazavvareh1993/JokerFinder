package com.example.jokerfinder.retrofit

import com.example.jokerfinder.pojoes.ResponseSearchMovie
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/movie")
    suspend fun getMovieSearched(@Query("api_key") apiKey : String, @Query("query") query: String, @Query("page") page : Int) : Response<ResponseSearchMovie>

}