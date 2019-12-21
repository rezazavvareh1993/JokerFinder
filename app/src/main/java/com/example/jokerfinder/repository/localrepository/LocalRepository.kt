package com.example.jokerfinder.repository.localrepository

import com.example.jokerfinder.base.BaseApplication
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.example.jokerfinder.utils.MovieDataBase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LocalRepository(application: BaseApplication) {

    private val movieDAO = MovieDataBase.getDatabase(application).getFavoriteMovieDAO()

    fun fetchAllFavoriteMovies() : Single<List<FavoriteMovieEntity>> {
        return movieDAO.getAllFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    fun insertFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) : Completable {
        return movieDAO.saveFavoriteMovie(favoriteMovieEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}