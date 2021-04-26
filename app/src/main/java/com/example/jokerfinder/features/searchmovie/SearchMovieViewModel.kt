package com.example.jokerfinder.features.searchmovie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jokerfinder.base.BaseViewModel
import com.example.jokerfinder.pojoes.ResultModel
import com.example.jokerfinder.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor (private val repository: DataRepository): ViewModel() {

    private var page = 1
    private var shouldLoadMore = true
    private var list = arrayListOf<ResultModel>()
    private var movieName = ""
    private var isLoading = false


    private var searchMovieMutableLiveData = MutableLiveData<List<ResultModel>>()
    private val disposable = CompositeDisposable()


    fun fetchMovieSearchData(movieName : String, isLoadMore : Boolean){
        if (isLoading) return

        if (movieName.isNotEmpty())
            this.movieName = movieName

        prepareDataToSearch(isLoadMore)

        disposable.add(
            repository.fetchMovieSearchData(movieName,page)
                .subscribe({
                    isLoading = false
                    if(it.results.isEmpty())
                      shouldLoadMore = false

                    list.addAll(it.results)
                    searchMovieMutableLiveData.value = list

                },{
                    isLoading = false
                    searchMovieMutableLiveData.value = null
                    Log.d("MyTag",it.message!!)
                })
        )
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

    fun getSearchMovieData() : LiveData<List<ResultModel>> = searchMovieMutableLiveData

    /**
     * Clearing the RX disposables
     */
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}