package com.example.jokerfinder.features.pagingtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.jokerfinder.base.BaseViewModel
import com.example.jokerfinder.pojoes.ResultModel
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.utils.MyConstantClass.Companion.APY_KEY

class SearchMovieViewModelTest(private val repository: DataRepository) : BaseViewModel() {

    private lateinit var userPagedList: LiveData<PagedList<ResultModel>>
    private lateinit var liveDataSource: LiveData<UserDataSource>
    private var movieName = MutableLiveData<String>()
    private var lastName = ""


    fun fetchMovieSearchData(movieName: String) {
        val itemDataSourceFactory =
            UserDataSourceFactory(viewModelScope, movieName, APY_KEY)
        liveDataSource = itemDataSourceFactory.userLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(UserDataSource.PAGE_SIZE)
            .build()
        userPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }

    fun setMovieName(myName: String){
        movieName.value = myName
        lastName = myName
    }
    fun getMovieName() : LiveData<String> = movieName

    fun isLastName() : Boolean = movieName.value == lastName


    fun getSearchMovieData(): LiveData<PagedList<ResultModel>> = userPagedList
}