package com.example.moviefinder.remote.movieFinderApi

import com.example.moviefinder.models.ResponseSearchMovieModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieFinderApiInterface {

    @GET("search/movie")
    fun getMovieDetailSearched(@Query("api_key") apiKey : String , @Query("query") query: String) : Observable<ResponseSearchMovieModel>
}
