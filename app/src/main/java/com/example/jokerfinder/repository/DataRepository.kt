package com.example.jokerfinder.repository

import android.annotation.SuppressLint
import com.example.jokerfinder.pojoes.Credits

import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.pojoes.ResponseSearchMovie
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import com.example.jokerfinder.utils.MyConstantClass
import io.reactivex.Single

class DataRepository {
    private val apiKey = MyConstantClass.APY_KEY

    private val networkRepository = NetworkRepository()


    @SuppressLint("CheckResult")
    fun fetchMovieDetails(idMovie: Int): Single<ResponseDetailMovie> {
        return networkRepository.fetchMovieDetails(idMovie, apiKey)
    }

    fun fetchCastsOfMovie(idMovie: Int): Single<Credits> {
        return networkRepository.fetchCastsOfMovie(idMovie, apiKey)
    }

    fun fetchMovieSearchData(movieName: String, page: Int): Single<ResponseSearchMovie> {
        return networkRepository.fetchMovieSearchData(movieName, apiKey, page)
    }
}