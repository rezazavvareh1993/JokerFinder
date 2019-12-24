package com.example.jokerfinder.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jokerfinder.base.BaseApplication
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO

@Database(entities = [FavoriteMovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun getFavoriteMovieDAO() : FavoriteMovieDAO
}