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

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MovieDataBase? = null

        fun getDatabase(application: BaseApplication): MovieDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {

                val instance = Room.databaseBuilder(application.applicationContext , MovieDataBase::class.java, "favoriteMovie").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}