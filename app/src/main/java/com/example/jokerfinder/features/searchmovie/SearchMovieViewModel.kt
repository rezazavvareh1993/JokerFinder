package com.example.jokerfinder.features.searchmovie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jokerfinder.R
import com.example.jokerfinder.pojoes.ResponseSearchMovie
import com.example.jokerfinder.repository.di.DaggerProvideRepositoryComponent
import com.example.jokerfinder.utils.MyConstantClass
import io.reactivex.disposables.CompositeDisposable


class SearchMovieViewModel : ViewModel() {

    private val component = DaggerProvideRepositoryComponent.create()
    private val repository = component.provideRepository()

    private var searchMovieMutableLiveData = MutableLiveData<ResponseSearchMovie>()
    private val disposable = CompositeDisposable()

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

    fun getSearchMovieData() : LiveData<ResponseSearchMovie> = searchMovieMutableLiveData

    /**
     * Clearing the RX disposables
     */
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}