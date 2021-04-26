package com.example.jokerfinder.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO

@Database(entities = [FavoriteMovieEntity::class], version = 2, exportSchema = false)
abstract class MovieDB : RoomDatabase() {
    abstract fun favoriteMovieDAO(): FavoriteMovieDAO

    companion object {
        @Volatile
        private var instance: MovieDB? = null

        fun getDatabase(context: Context): MovieDB =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, MovieDB::class.java, "favoriteMovie")
                .fallbackToDestructiveMigration()
                .build()
    }
}