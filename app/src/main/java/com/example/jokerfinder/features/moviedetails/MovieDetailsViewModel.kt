package com.example.jokerfinder.features.moviedetails

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseViewModel
import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.utils.MyConstantClass
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel(private  val repository: DataRepository) : BaseViewModel() {
    private var movieDetailsLiveData = MutableLiveData<ResponseDetailMovie>()
    private val disposable = CompositeDisposable()


    fun fetchData (idMovie : Int, context : Context){
        disposable.add(repository.fetchMovieDetails(idMovie)
            .subscribe({
                movieDetailsLiveData.value = it
            },{
                Log.d("MyTag", it.message)
                MyConstantClass.showToast(context, context.resources.getString(R.string.error_connection))
                movieDetailsLiveData.value = null
            })
        )
    }

    fun getMovieDetailsData() : LiveData<ResponseDetailMovie> = movieDetailsLiveData

    /**
     * Clearing the RX disposables
     */
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}

