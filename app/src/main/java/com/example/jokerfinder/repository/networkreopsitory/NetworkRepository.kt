package com.example.jokerfinder.repository.networkreopsitory

import com.example.jokerfinder.pojoes.Credits
import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.pojoes.ResponseSearchMovie
import com.example.jokerfinder.retrofit.DaggerProvideRetrofitComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NetworkRepository @Inject constructor() {

    private val retrofitService = DaggerProvideRetrofitComponent.create().getRetrofitApiService()

    fun fetchMovieDetails(idMovie: Int, apiKey: String): Single<ResponseDetailMovie> {
        return retrofitService
            .getMovieDetails(
                idMovie,
                apiKey
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchCastsOfMovie(idMovie: Int, apiKey : String) : Single<Credits>{
        return retrofitService
            .getCastsOfMovie(
                idMovie,
                apiKey
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

        fun fetchMovieSearchData(movieName : String, apiKey: String, page : Int) : Single<ResponseSearchMovie>{
            return retrofitService
                .getMovieSearched(
                    apiKey,
                    movieName,
                    page
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
}