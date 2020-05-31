package com.example.jokerfinder.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO

@Database(entities = [FavoriteMovieEntity::class], version = 2, exportSchema = false)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun getFavoriteMovieDAO() : FavoriteMovieDAO
}