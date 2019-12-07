package com.example.jokerfinder.retrofit

import com.example.jokerfinder.pojoes.Credits
import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.pojoes.ResponseSearchMovieModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieFinderApi {

    @GET("search/movie")
    fun getMovieDetailSearched(@Query("api_key") apiKey : String , @Query("query") query: String) : Single<ResponseSearchMovieModel>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")movieId : Int, @Query("api_key") apiKey : String ) : Single<ResponseDetailMovie>

    @GET("movie/{movie_id}/credits")
    fun getCastsOfMovie(@Path("movie_id")movieId: Int, @Query("api_key") apiKey: String) : Single<Credits>
}
