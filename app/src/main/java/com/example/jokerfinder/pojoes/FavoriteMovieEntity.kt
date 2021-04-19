package com.example.jokerfinder.pojoes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteMovie")
class FavoriteMovieEntity (
    @PrimaryKey(autoGenerate = true)
    var movieId: Int,
    val movieName : String,
    val movieReleaseDate : String,
    val movieRate : Double,
    val moviePicUrl : String
)