package com.example.jokerfinder.features.moviedetails.castsofmovie

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokerfinder.pojoes.Credits
import com.example.jokerfinder.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastOfMovieViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {
    private var castOfMovieMutableLiveData = MutableLiveData<Credits>()

    fun fetchCastOfMovieData(idMovie: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.fetchCastsOfMovie(idMovie).collect {
                    castOfMovieMutableLiveData.postValue(it)
                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                castOfMovieMutableLiveData.postValue(null)
            }
        }
    }

    fun getCastOfMovieData(): LiveData<Credits> = castOfMovieMutableLiveData
}