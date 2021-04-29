package com.example.jokerfinder.features.searchmovie

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokerfinder.pojoes.ResultModel
import com.example.jokerfinder.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {

    private var page = 1
    private var shouldLoadMore = true
    private var list = arrayListOf<ResultModel>()
    private var movieName = ""
    private var isLoading = false

    private var searchMovieMutableLiveData = MutableLiveData<List<ResultModel>>()

    fun fetchMovieSearchData(movieName: String, isLoadMore: Boolean) {
        if (isLoading) return

        if (movieName.isNotEmpty())
            this.movieName = movieName

        prepareDataToSearch(isLoadMore)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.fetchMovieSearchData(movieName, page).collect {
                    isLoading = false
                    if (it?.results?.isEmpty()!!)
                        shouldLoadMore = false

                    list.addAll(it.results)
                    searchMovieMutableLiveData.value = list
                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                isLoading = false
                searchMovieMutableLiveData.value = null
            }
        }
    }

    private fun prepareDataToSearch(isLoadMore: Boolean) {
        isLoading = true
        if (isLoadMore && shouldLoadMore) {
            page++
        } else {
            page = 1
            list.clear()
            shouldLoadMore = true
        }
    }

    fun getSearchMovieData(): LiveData<List<ResultModel>> = searchMovieMutableLiveData
}