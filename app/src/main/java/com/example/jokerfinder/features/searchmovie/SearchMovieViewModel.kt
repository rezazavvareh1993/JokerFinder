package com.example.jokerfinder.features.searchmovie

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jokerfinder.features.searchmovie.movieadapter.MoviePageResource
import com.example.jokerfinder.pojo.ResultModel
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.utils.response.GeneralResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {

    private var searchData = MutableLiveData<GeneralResponse<PagingData<ResultModel>>>()

    fun fetchMovieSearchData(movieName: String) {
        viewModelScope.launch {
            try {
                searchData.value = GeneralResponse.Loading()
                val newDataFlow = Pager(PagingConfig(pageSize = 10)) {
                    MoviePageResource(repository, movieName)
                }.flow.cachedIn(viewModelScope)
                searchData.value = GeneralResponse.Success(newDataFlow.asLiveData().value)
            } catch (e: Exception) {
                searchData.value = GeneralResponse.Error(e.message.toString())
            }
        }
    }

    fun gerSearchData() = searchData
}

