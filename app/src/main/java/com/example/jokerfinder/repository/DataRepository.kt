package com.example.jokerfinder.repository

import android.annotation.SuppressLint
import com.example.jokerfinder.pojoes.Credits

import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.pojoes.ResponseSearchMovieModel
import com.example.jokerfinder.retrofit.RetrofitProvideClass
import com.example.jokerfinder.utils.MyConstantClass
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataRepository {
    private val apiKey = MyConstantClass.APY_KEY


    @SuppressLint("CheckResult")
    fun fetchMovieDetails(idMovie : Int) : Observable<ResponseDetailMovie> {
        return RetrofitProvideClass.provideRetrofit()
                .getMovieDetails(
                    idMovie,
                    apiKey
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchCastsOfMovie(idMovie: Int) : Observable<Credits>{
        return RetrofitProvideClass.provideRetrofit()
            .getCastsOfMovie(
                idMovie,
                apiKey
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchMovieSearchData(movieName : String) : Observable<ResponseSearchMovieModel>{
        return RetrofitProvideClass.provideRetrofit()
            .getMovieDetailSearched(
                apiKey,
                movieName
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}