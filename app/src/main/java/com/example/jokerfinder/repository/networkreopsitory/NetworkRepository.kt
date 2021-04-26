package com.example.jokerfinder.repository.networkreopsitory

import com.example.jokerfinder.pojoes.Credits
import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.pojoes.ResponseSearchMovie
import com.example.jokerfinder.retrofit.JokerFinderApiService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val retrofitService: JokerFinderApiService) {

    suspend fun fetchMovieDetails(idMovie: Int, apiKey: String): Response<ResponseDetailMovie> {
        return retrofitService.getMovieDetails(
                idMovie,
                apiKey
            )
    }

    fun fetchCastsOfMovie(idMovie: Int, apiKey: String): Single<Credits> {
        return retrofitService
            .getCastsOfMovie(
                idMovie,
                apiKey
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchMovieSearchData(
        movieName: String,
        page: Int,
        apiKey: String
    ): Single<ResponseSearchMovie> {
        return retrofitService
            .getMovieSearched(
                apiKey,
                movieName,
                page
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

//test
}