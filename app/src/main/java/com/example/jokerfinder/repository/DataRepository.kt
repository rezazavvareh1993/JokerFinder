package com.example.jokerfinder.repository

import android.annotation.SuppressLint
import com.example.jokerfinder.pojoes.Credits

import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.pojoes.ResponseSearchMovie
import com.example.jokerfinder.repository.localrepository.LocalRepository
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import com.example.jokerfinder.utils.MyConstantClass
import io.reactivex.Single

class DataRepository(val networkRepository: NetworkRepository, val localRepository: LocalRepository) {
    private val apiKey = MyConstantClass.APY_KEY



    /////////////////////////////////////////Network

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

    ////////////////////////////////Local
}