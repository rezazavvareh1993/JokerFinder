package com.example.jokerfinder.repository

import com.example.jokerfinder.base.db.FavoriteMovieEntity
import com.example.jokerfinder.pojo.ResponseSearchMovie
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import com.example.jokerfinder.utils.MyConstantClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val favoriteMovieDAO: FavoriteMovieDAO
) {
    private val apiKey = MyConstantClass.APY_KEY

    //Network
    fun fetchMovieDetails(idMovie: Int) = flow {
        val response = networkRepository.fetchMovieDetails(idMovie, apiKey)
        emit(response)
    }

    fun fetchCastsOfMovie(idMovie: Int) = flow {
        val response = networkRepository.fetchCastsOfMovie(idMovie, apiKey)
        emit(response)
    }

    suspend fun fetchMovieSearchData(movieName: String, page: Int): ResponseSearchMovie =
        networkRepository.fetchMovieSearchData(movieName, page, apiKey)

    //Local
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

    fun findByMovieId(movieId: Int): FavoriteMovieEntity? {
        return favoriteMovieDAO.findByMovieId(movieId)
    }

}