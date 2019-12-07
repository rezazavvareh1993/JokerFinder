package com.example.jokerfinder.features.searchmovie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jokerfinder.R
import com.example.jokerfinder.pojoes.ResponseSearchMovieModel
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.utils.MyConstantClass
import io.reactivex.disposables.CompositeDisposable


class SearchMovieViewModel : ViewModel() {

    private var searchMovieMutableLiveData = MutableLiveData<ResponseSearchMovieModel>()
    private val disposable = CompositeDisposable()
    private val repository = DataRepository()


    fun fetchMovieSearchData(movieName : String, context: Context){
        disposable.add(
            repository.fetchMovieSearchData(movieName)
                .subscribe({
                    searchMovieMutableLiveData.value = it

                },{
                    MyConstantClass.showToast(context, context.resources.getString(R.string.error_connection))
                    searchMovieMutableLiveData.value = null

                })
        )
    }

    fun getSearchMovieData() : LiveData<ResponseSearchMovieModel> = searchMovieMutableLiveData
}