package com.example.jokerfinder.repository.localrepository

import androidx.room.*
import com.example.jokerfinder.features.favoritemovies.FavoriteMovieViewModel
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteMovieDAO {

    @Query("SELECT * FROM favoriteMovie")
    fun getAllFavoriteMovies(): Single<List<FavoriteMovieEntity>>

    @Query("SELECT * FROM favoriteMovie WHERE movieId LIKE :movieId")
    fun findByMovieId(movieId: Int): Single<FavoriteMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) : Completable

    @Delete
    fun delete(favoriteMovieEntity: FavoriteMovieEntity) : Completable

    @Update
    fun update(favoriteMovieEntity: FavoriteMovieEntity) : Completable
}