package com.example.jokerfinder.pojoes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteMovie")
class FavoriteMovieEntity (
    @PrimaryKey(autoGenerate = true)
    var idMovie: Int,
    @ColumnInfo(name = "movieName") var movieName : String,
    @ColumnInfo(name = "movieReleaseDate") var movieReleaseDate : String,
    @ColumnInfo(name = "movieRate") var movieRate : Double,
    @ColumnInfo(name = "moviePicUrl") var moviePicUrl : String
)