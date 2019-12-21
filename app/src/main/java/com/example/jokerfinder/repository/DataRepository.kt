package com.example.jokerfinder.repository

import android.annotation.SuppressLint
import com.example.jokerfinder.base.BaseApplication
import com.example.jokerfinder.pojoes.Credits
import com.example.jokerfinder.pojoes.FavoriteMovieEntity

import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.pojoes.ResponseSearchMovie
import com.example.jokerfinder.repository.localrepository.LocalRepository
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import com.example.jokerfinder.utils.MyConstantClass
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataRepository(private val networkRepository: NetworkRepository, baseApplication : BaseApplication ) {

    private val apiKey = MyConstantClass.APY_KEY
    private val localRepository  = LocalRepository(baseApplication)

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

    fun fetchAllFavoriteMovies() : Single<List<FavoriteMovieEntity>> {
        return localRepository.fetchAllFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) : Completable{
        return localRepository.insertFavoriteMovie(favoriteMovieEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}