package com.example.jokerfinder.repository

import android.annotation.SuppressLint
import com.example.jokerfinder.pojoes.Credits

import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.pojoes.ResponseSearchMovie
import com.example.jokerfinder.retrofit.RetrofitProvideClass
import com.example.jokerfinder.utils.MyConstantClass
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DataRepository @Inject constructor() {
    private val apiKey = MyConstantClass.APY_KEY


    @SuppressLint("CheckResult")
    fun fetchMovieDetails(idMovie : Int) : Single<ResponseDetailMovie> {
        return RetrofitProvideClass.provideRetrofit()
                .getMovieDetails(
                    idMovie,
                    apiKey
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchCastsOfMovie(idMovie: Int) : Single<Credits>{
        return RetrofitProvideClass.provideRetrofit()
            .getCastsOfMovie(
                idMovie,
                apiKey
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchMovieSearchData(movieName : String) : Single<ResponseSearchMovie>{
        return RetrofitProvideClass.provideRetrofit()
            .getMovieDetailSearched(
                apiKey,
                movieName
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}