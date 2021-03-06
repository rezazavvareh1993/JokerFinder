package com.example.jokerfinder.features.pagingtest

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.jokerfinder.pojoes.ResultModel
import kotlinx.coroutines.Dispatchers

/**
 * Created by Morris on 03,June,2019
 */
class UserDataSourceFactory(private val searchedName: String, private val apiKey: String) : DataSource.Factory<Int, ResultModel>() {
    val userLiveDataSource = MutableLiveData<UserDataSource>()
    override fun create(): DataSource<Int, ResultModel> {
        val userDataSource =
            UserDataSource(Dispatchers.IO ,searchedName, apiKey)
        userLiveDataSource.postValue(userDataSource)
        return userDataSource
    }
}