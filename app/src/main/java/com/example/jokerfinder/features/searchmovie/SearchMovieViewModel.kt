package com.example.jokerfinder.features.searchmovie

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

    lateinit var dataFlow: Flow<PagingData<ResultModel>>
    private var lastName = ""
    private var movieName = ""

    fun fetchMovieSearchData() {
        if (lastName == movieName) return
        dataFlow = Pager(PagingConfig(pageSize = 10)) {
            MoviePageResource(repository, movieName)
        }.flow.cachedIn(viewModelScope)
        lastName = movieName
    }

    fun setMovieName(name: String) {
        movieName = name
    }

    fun getMovieName() = movieName

    fun isSameName() = movieName == lastName
}
