package com.example.jokerfinder.features.favoritemovies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jokerfinder.base.BaseViewModel
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.example.jokerfinder.repository.DataRepository
import io.reactivex.disposables.CompositeDisposable

class FavoriteMovieViewModel(val repository: DataRepository) : BaseViewModel() {
    private var movieListMutableLiveData = MutableLiveData<List<FavoriteMovieEntity>>()
    private var isMovieInDataBaseMutableLiveData = MutableLiveData<Boolean>()
    private val compositeDisposable = CompositeDisposable()

    /////////////////////////get all favoriteMovie from dataBase
    fun fetchAllFavoriteMovies (){
        compositeDisposable.add(
            repository.fetchAllFavoriteMovies()
                .subscribe({
                    if(it!=null)
                        movieListMutableLiveData.value = it
                    else
                        movieListMutableLiveData.value = null
                },{ Log.d("MyTag", it.message!!)})
        )
    }

    fun getAllFavoriteMovies() : LiveData<List<FavoriteMovieEntity>> {
        return movieListMutableLiveData
    }

    //////////////////////insert Movie to dataBase
    fun insertFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) {
        compositeDisposable.add(repository.insertFavoriteMovie(favoriteMovieEntity)
            .subscribe({
                Log.d("MyTag", "insert ${favoriteMovieEntity.movieName}")
            },{
                Log.d("MyTag", it.message!!)
            }))
    }

    ////////////////////////delete Movie from dataBase
    fun deleteMovieFromFavoriteMovies(favoriteMovieEntity: FavoriteMovieEntity){
        compositeDisposable.add(repository.deleteMovieFromFavoriteMovies(favoriteMovieEntity)
            .subscribe ({
                Log.d("MyTag", "delete ${favoriteMovieEntity.movieName}")
            }, {
                Log.d("MyTag", it.message!!)
            }))
    }

    ///////////////////////////findMovieByData
    fun findMovieByMovieId(movieId : Int ){
        compositeDisposable.add(repository.findByMovieId(movieId)
            .subscribe({
                isMovieInDataBaseMutableLiveData.value =true
            },{
                Log.d("MyTag", it.message!!)
                isMovieInDataBaseMutableLiveData.value =false
            }))
    }

    fun getIsMovieInDataBase() : LiveData<Boolean>{
        return isMovieInDataBaseMutableLiveData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
