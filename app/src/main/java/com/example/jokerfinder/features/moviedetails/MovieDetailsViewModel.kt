package com.example.jokerfinder.features.moviedetails

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokerfinder.R
import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.utils.MyConstantClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {
    private var movieDetailsLiveData = MutableLiveData<ResponseDetailMovie>()

//    viewModelScope.launch {
//        try {
//
//        } catch (e: Exception) {
//            Log.d(TAG, e.message.toString())
//        }
//    }

    fun fetchMovieDetails(idMovie: Int, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.fetchMovieDetails(idMovie).collect {
                    movieDetailsLiveData.value = it
                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                MyConstantClass.showToast(
                    context,
                    context.resources.getString(R.string.error_connection)
                )
                movieDetailsLiveData.value = null
            }
        }
    }

    fun getMovieDetailsData(): LiveData<ResponseDetailMovie> = movieDetailsLiveData
}

