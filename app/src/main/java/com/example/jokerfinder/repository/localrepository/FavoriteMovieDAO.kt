package com.example.jokerfinder.repository.localrepository

import androidx.room.*
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteMovieDAO {

    @Query("SELECT * FROM favoriteMovie")
    fun getAllFavoriteMovies(): Single<List<FavoriteMovieEntity>>

//    @Query("SELECT * FROM favorite_movies WHERE title LIKE :title")
//    fun findByTitle(title: String): Entity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) : Completable

    @Delete
    fun delete(favoriteMovieEntity: FavoriteMovieEntity)

    @Update
    fun update(favoriteMovieEntity: FavoriteMovieEntity)
//
}