package com.example.jokerfinder.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO

@Database(entities = [FavoriteMovieEntity::class], version = 2, exportSchema = false)
abstract class MovieDB : RoomDatabase() {
    /**
     * Connects the database to the DAO.
     */
    abstract val favoriteMovieDAO: FavoriteMovieDAO

    companion object {

        @Volatile
        private var INSTANCE: MovieDB? = null

        fun getInstance(context: Context): MovieDB {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {

                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE

                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDB::class.java,
                        "sleep_history_database"
                    )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }

                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}