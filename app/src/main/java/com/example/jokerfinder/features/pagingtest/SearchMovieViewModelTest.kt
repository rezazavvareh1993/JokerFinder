package com.example.jokerfinder.features.pagingtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.jokerfinder.base.BaseViewModel
import com.example.jokerfinder.pojoes.ResultModel
import com.example.jokerfinder.repository.DataRepository
import io.reactivex.disposables.CompositeDisposable
import androidx.paging.LivePagedListBuilder
import com.example.jokerfinder.features.pagingtest.UserDataSource
import com.example.jokerfinder.features.pagingtest.UserDataSourceFactory
import com.example.jokerfinder.utils.MyConstantClass

class SearchMovieViewModelTest (private val repository: DataRepository): BaseViewModel() {

    lateinit var userPagedList: LiveData<PagedList<ResultModel>>
    private lateinit var liveDataSource: LiveData<UserDataSource>

    fun fetchMovieSearchData(movieName : String){
        val itemDataSourceFactory =
            UserDataSourceFactory(movieName,  MyConstantClass.APY_KEY)
        liveDataSource = itemDataSourceFactory.userLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(UserDataSource.PAGE_SIZE)
            .build()
        userPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }

    fun getSearchMovieData() : LiveData<PagedList<ResultModel>> = userPagedList
}