package com.example.jokerfinder.base.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteMovie")
class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    var movieId: Int,
    val movieName: String,
    val movieReleaseDate: String?,
    val movieRate: Double?,
    val moviePicUrl: String?
)
