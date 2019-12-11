package com.example.jokerfinder.repository

import android.annotation.SuppressLint
import com.example.jokerfinder.pojoes.Credits

import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.pojoes.ResponseSearchMovie
import com.example.jokerfinder.repository.networkreopsiroty.NetworkReposiitory
import com.example.jokerfinder.retrofit.RetrofitProvideClass
import com.example.jokerfinder.utils.MyConstantClass
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DataRepository @Inject constructor() {
    private val apiKey = MyConstantClass.APY_KEY

    private lateinit var networkReposiitory : NetworkReposiitory
    private


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

    fun fetchMovieSearchData(movieName : String, page : Int) : Single<ResponseSearchMovie>{
        return RetrofitProvideClass.provideRetrofit()
            .getMovieDetailSearched(
                apiKey,
                movieName,
                page
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}