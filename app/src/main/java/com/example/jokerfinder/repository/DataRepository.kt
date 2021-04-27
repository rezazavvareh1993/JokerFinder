package com.example.jokerfinder.repository

import android.annotation.SuppressLint
import com.example.jokerfinder.pojoes.Credits
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.pojoes.ResponseSearchMovie
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import com.example.jokerfinder.utils.MyConstantClass
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val favoriteMovieDAO: FavoriteMovieDAO
) {

    private val apiKey = MyConstantClass.APY_KEY

    /////////////////////////////////////////Network

    @SuppressLint("CheckResult")
    fun fetchMovieDetails(idMovie: Int) = flow {
        val response = networkRepository.fetchMovieDetails(idMovie, apiKey)
        if (response.isSuccessful)
            emit(response.body())
    }.flowOn(Dispatchers.IO)

    fun fetchCastsOfMovie(idMovie: Int) = flow {
        val response = networkRepository.fetchCastsOfMovie(idMovie, apiKey)
        if(response.isSuccessful)
            emit(response.body())
    }.flowOn(Dispatchers.IO)

    fun fetchMovieSearchData(movieName: String, page: Int): Single<ResponseSearchMovie> {
        return networkRepository.fetchMovieSearchData(movieName, page, apiKey)
    }
    //test

    ////////////////////////////////Local

    fun fetchAllFavoriteMovies(): Single<List<FavoriteMovieEntity>> {
        return favoriteMovieDAO.getAllFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity): Completable {
        return favoriteMovieDAO.saveFavoriteMovie(favoriteMovieEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteMovieFromFavoriteMovies(favoriteMovieEntity: FavoriteMovieEntity): Completable {
        return favoriteMovieDAO.delete(favoriteMovieEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun findByMovieId(movieId: Int): Single<FavoriteMovieEntity> {
        return favoriteMovieDAO.findByMovieId(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}