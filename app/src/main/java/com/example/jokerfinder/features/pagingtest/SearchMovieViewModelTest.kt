package com.example.jokerfinder.features.pagingtest

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.jokerfinder.base.BaseViewModel
import com.example.jokerfinder.pojoes.ResultModel
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.utils.MyConstantClass.Companion.APY_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchMovieViewModelTest(private val repository: DataRepository) : BaseViewModel() {

    private lateinit var userPagedList: LiveData<PagedList<ResultModel>>
    private lateinit var liveDataSource: LiveData<UserDataSource>

    fun fetchMovieSearchData(movieName: String) {
        val itemDataSourceFactory =
            UserDataSourceFactory(movieName, APY_KEY)
        liveDataSource = itemDataSourceFactory.userLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(UserDataSource.PAGE_SIZE)
            .build()
        userPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }

    fun getSearchMovieData(): LiveData<PagedList<ResultModel>> = userPagedList
}