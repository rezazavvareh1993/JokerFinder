package com.example.jokerfinder.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import com.example.jokerfinder.utils.MyConstantClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
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
        if (response.isSuccessful)
            emit(response.body())
    }.flowOn(Dispatchers.IO)

    fun fetchMovieSearchData(movieName: String, page: Int) = flow {
        val response = networkRepository.fetchMovieSearchData(movieName, page, apiKey)
        if (response.isSuccessful)
            emit(response.body())
    }.flowOn(Dispatchers.IO)

    ////////////////////////////////Local

    fun fetchAllFavoriteMovies() = flow {
        val data = favoriteMovieDAO.getAllFavoriteMovies()
        if (!data.isNullOrEmpty())
            emit(data)
    }.flowOn(Dispatchers.IO)

    suspend fun insertFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) {
        favoriteMovieDAO.saveFavoriteMovie(favoriteMovieEntity)
    }

    suspend fun deleteMovieFromFavoriteMovies(favoriteMovieEntity: FavoriteMovieEntity) {
        favoriteMovieDAO.delete(favoriteMovieEntity)
    }

    fun findByMovieId(movieId: Int): LiveData<FavoriteMovieEntity> {
        val x = favoriteMovieDAO.findByMovieId(movieId)
        return  x
    }

}