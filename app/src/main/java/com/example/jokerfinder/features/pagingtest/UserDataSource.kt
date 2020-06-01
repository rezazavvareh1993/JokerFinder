package com.example.jokerfinder.features.pagingtest

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.jokerfinder.pojoes.ResultModel
import com.example.jokerfinder.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Morris on 03,June,2019
 */
class UserDataSource(
    coroutineContext: CoroutineContext,
    private val searchedName: String,
    private val apiKey: String
) : PageKeyedDataSource<Int, ResultModel>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultModel>) {
        val service = ApiServiceBuilder.getClient().create(ApiService::class.java)
        scope.launch {
            try {
                val response = service.getMovieSearched(apiKey, searchedName, params.key)
                when {
                    response.isSuccessful -> {
                        val apiResponse = response.body()!!
                        val responseItems = apiResponse.results
                        val key = if (params.key > 1)
                            params.key - 1
                        else
                            0
                        responseItems?.let {
                            callback.onResult(responseItems, key)
                        }
                    }
                }
            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultModel>
    ) {
        val service = ApiServiceBuilder.getClient().create(ApiService::class.java)
        scope.launch {
            try {
                val response = service.getMovieSearched(apiKey, searchedName, FIRST_PAGE)
                when {
                    response.isSuccessful -> {
                        val apiResponse = response.body()!!
                        val responseItems = apiResponse.results
                        responseItems?.let {
                            callback.onResult(responseItems, null, FIRST_PAGE + 1)
                        }
                    }
                }
            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultModel>) {
        val service = ApiServiceBuilder.getClient().create(ApiService::class.java)
        scope.launch {
            try {
                val response = service.getMovieSearched(apiKey, searchedName, params.key)
                when {
                    response.isSuccessful -> {
                        val apiResponse = response.body()!!
                        val responseItems = apiResponse.results
                        val key = params.key + 1
                        responseItems?.let {
                            callback.onResult(responseItems, key)
                        }
                    }
                }
            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 6
        const val FIRST_PAGE = 1
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}