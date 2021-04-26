package com.example.jokerfinder.features.moviedetails

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseViewModel
import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.utils.MyConstantClass
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private  val repository: DataRepository) : ViewModel() {
    private var movieDetailsLiveData = MutableLiveData<ResponseDetailMovie>()
    private val disposable = CompositeDisposable()


    fun fetchMovieDetails (idMovie : Int, context : Context){
        disposable.add(repository.fetchMovieDetails(idMovie)
            .subscribe({
                movieDetailsLiveData.value = it
            },{
                Log.d("MyTag", it.message!!)
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

