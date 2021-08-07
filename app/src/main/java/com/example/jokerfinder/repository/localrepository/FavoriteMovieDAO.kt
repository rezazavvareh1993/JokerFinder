package com.example.jokerfinder.repository.localrepository

import androidx.room.* // ktlint-disable no-wildcard-imports
import com.example.jokerfinder.base.db.FavoriteMovieEntity

@Dao
interface FavoriteMovieDAO {

    @Query("SELECT * FROM favoriteMovie")
    suspend fun getAllFavoriteMovies(): List<FavoriteMovieEntity>

    @Query("SELECT * FROM favoriteMovie WHERE movieId LIKE :movieId")
    fun findByMovieId(movieId: Int): FavoriteMovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity)

    @Delete
    suspend fun delete(favoriteMovieEntity: FavoriteMovieEntity)

    @Update
    suspend fun update(favoriteMovieEntity: FavoriteMovieEntity)
}
