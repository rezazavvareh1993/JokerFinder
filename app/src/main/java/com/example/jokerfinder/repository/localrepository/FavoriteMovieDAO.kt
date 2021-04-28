package com.example.jokerfinder.repository.localrepository

import androidx.room.*
import com.example.jokerfinder.features.favoritemovies.FavoriteMovieViewModel
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteMovieDAO {

    @Query("SELECT * FROM favoriteMovie")
    suspend fun getAllFavoriteMovies(): List<FavoriteMovieEntity>

    @Query("SELECT * FROM favoriteMovie WHERE movieId LIKE :movieId")
    suspend fun findByMovieId(movieId: Int): FavoriteMovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity)

    @Delete
    suspend fun delete(favoriteMovieEntity: FavoriteMovieEntity)

    @Update
    suspend fun update(favoriteMovieEntity: FavoriteMovieEntity)
}