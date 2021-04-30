package com.example.jokerfinder.features.favoritemovies

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.example.jokerfinder.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {

    private var movieListMutableLiveData = MutableLiveData<List<FavoriteMovieEntity>>()
    private var isMovieInDataBaseMutableLiveData = MutableLiveData<Boolean>()

    /////////////////////////get all favoriteMovie from dataBase
    fun fetchAllFavoriteMovies() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.fetchAllFavoriteMovies().collect {
                    movieListMutableLiveData.postValue(it)
                }
            } catch (e: Exception) {
                movieListMutableLiveData.postValue(null)
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovieEntity>> {
        return movieListMutableLiveData
    }

    //////////////////////insert Movie to dataBase
    fun insertFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) {

        viewModelScope.launch {
            try {
                repository.insertFavoriteMovie(favoriteMovieEntity)
                Log.d("MyTag", "insert ${favoriteMovieEntity.movieName}")
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    ////////////////////////delete Movie from dataBase
    fun deleteMovieFromFavoriteMovies(favoriteMovieEntity: FavoriteMovieEntity) {
        viewModelScope.launch {
            try {
                repository.deleteMovieFromFavoriteMovies(favoriteMovieEntity)
                Log.d("MyTag", "delete ${favoriteMovieEntity.movieName}")
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    ///////////////////////////findMovieByData
    fun findMovieByMovieId(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = repository.findByMovieId(movieId)
                if (data?.value != null)
                    isMovieInDataBaseMutableLiveData.postValue(true)
                else
                    isMovieInDataBaseMutableLiveData.postValue(false)
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                isMovieInDataBaseMutableLiveData.postValue(false)
            }
        }
    }

    fun getIsMovieInDataBase(): LiveData<Boolean> {
        return isMovieInDataBaseMutableLiveData
    }
}
