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
    val compositeDisposable = CompositeDisposable()

    fun fetchAllFavoriteMovies (){
        compositeDisposable.add(
            repository.fetchAllFavoriteMovies()
                .subscribe({
                    if(it!=null)
                        movieListMutableLiveData.value = it
                    else
                        movieListMutableLiveData.value = null
                },{ Log.d("MyTag", it.message)})
        )
    }

    fun getAllFavoriteMovies() : LiveData<List<FavoriteMovieEntity>> {
        return movieListMutableLiveData
    }

    fun insertFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) {
        compositeDisposable.add(repository.insertFavoriteMovie(favoriteMovieEntity)
            .subscribe({
                Log.d("MyTag", "insert ${favoriteMovieEntity.movieName}")
            },{
                Log.d("MyTag", it.message)
            }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
