package com.example.jokerfinder.features.searchmovie

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jokerfinder.features.searchmovie.movieadapter.MoviePageResource
import com.example.jokerfinder.pojo.ResultModel
import com.example.jokerfinder.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {

    private var flowData: Flow<PagingData<ResultModel>>? = null

    fun fetchMovieSearchData(movieName: String): Flow<PagingData<ResultModel>>? {
        val newDataFlow = Pager(PagingConfig(pageSize = 10)) {
            MoviePageResource(repository, movieName)
        }.flow.cachedIn(viewModelScope)
        flowData = newDataFlow
        return flowData
    }
}