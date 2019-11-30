package com.example.moviefinder.remote.movieFinderApi

import com.example.moviefinder.models.ResponseDetailMovie
import com.example.moviefinder.models.ResponseSearchMovieModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieFinderApi {

    @GET("search/movie")
    fun getMovieDetailSearched(@Query("api_key") apiKey : String , @Query("query") query: String) : Observable<ResponseSearchMovieModel>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")movieId : Int, @Query("api_key") apiKey : String ) : Observable<ResponseDetailMovie>
}
