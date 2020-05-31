package com.example.jokerfinder.features.pagingtest

import androidx.paging.PageKeyedDataSource
import com.example.jokerfinder.pojoes.ResponseSearchMovie
import com.example.jokerfinder.pojoes.ResultModel
import com.example.jokerfinder.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Morris on 03,June,2019
 */
class UserDataSource(private val searchedName: String, private val apiKey: String) : PageKeyedDataSource<Int, ResultModel>() {
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultModel>) {
        val service = ApiServiceBuilder.buildService(ApiService::class.java)
        val call = service.getMovieSearched(apiKey, searchedName, params.key)
        call.enqueue(object : Callback<ResponseSearchMovie> {
            override fun onResponse(call: Call<ResponseSearchMovie>, response: Response<ResponseSearchMovie>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.results
                    val key = if (params.key > 1) params.key - 1 else 0
                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseSearchMovie>, t: Throwable) {
            }
        })
    }
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ResultModel>) {
        val service = ApiServiceBuilder.buildService(ApiService::class.java)
        val call = service.getMovieSearched(apiKey, searchedName, FIRST_PAGE)
        call.enqueue(object : Callback<ResponseSearchMovie> {
            override fun onResponse(call: Call<ResponseSearchMovie>, response: Response<ResponseSearchMovie>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.results
                    responseItems?.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 1)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseSearchMovie>, t: Throwable) {
            }
        })
    }
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultModel>) {
        val service = ApiServiceBuilder.buildService(ApiService::class.java)
        val call = service.getMovieSearched(apiKey, searchedName, params.key)
        call.enqueue(object : Callback<ResponseSearchMovie> {
            override fun onResponse(call: Call<ResponseSearchMovie>, response: Response<ResponseSearchMovie>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.results
                    val key = params.key + 1
                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseSearchMovie>, t: Throwable) {
            }
        })
    }
    companion object {
        const val PAGE_SIZE = 6
        const val FIRST_PAGE = 1
    }
}