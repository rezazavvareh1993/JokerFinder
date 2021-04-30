package com.example.jokerfinder.repository.localrepository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jokerfinder.base.db.FavoriteMovieEntity

@Dao
interface FavoriteMovieDAO {

    @Query("SELECT * FROM favoriteMovie")
    suspend fun getAllFavoriteMovies(): List<FavoriteMovieEntity>

    @Query("SELECT * FROM favoriteMovie WHERE movieId LIKE :movieId")
    fun findByMovieId(movieId: Int): LiveData<FavoriteMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity)

    @Delete
    suspend fun delete(favoriteMovieEntity: FavoriteMovieEntity)

    @Update
    suspend fun update(favoriteMovieEntity: FavoriteMovieEntity)
}