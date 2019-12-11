package com.example.jokerfinder.repository.networkreopsitory

import com.example.jokerfinder.pojoes.Credits
import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.pojoes.ResponseSearchMovie
import com.example.jokerfinder.retrofit.MovieFinderApi
import com.example.jokerfinder.retrofit.RetrofitProvideClass
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NetworkRepository () {

    fun fetchMovieDetails(idMovie: Int, apiKey: String): Single<ResponseDetailMovie> {
        return RetrofitProvideClass.provideRetrofit()
            .getMovieDetails(
                idMovie,
                apiKey
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchCastsOfMovie(idMovie: Int, apiKey : String) : Single<Credits>{
        return RetrofitProvideClass.provideRetrofit()
            .getCastsOfMovie(
                idMovie,
                apiKey
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

        fun fetchMovieSearchData(movieName : String, apiKey: String, page : Int) : Single<ResponseSearchMovie>{
            return RetrofitProvideClass.provideRetrofit()
                .getMovieSearched(
                    apiKey,
                    movieName,
                    page
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
}