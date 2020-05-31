package com.example.jokerfinder.retrofit

import com.example.jokerfinder.pojoes.ResponseSearchMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/movie")
    fun getMovieSearched(@Query("api_key") apiKey : String, @Query("query") query: String, @Query("page") page : Int) : Call<ResponseSearchMovie>

}