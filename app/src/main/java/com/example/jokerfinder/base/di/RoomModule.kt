package com.example.jokerfinder.base.di

import android.app.Application
import androidx.room.Room
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO
import com.example.jokerfinder.utils.MovieDataBase
import dagger.Module
import dagger.Provides

@Module
class RoomModule(private val application: Application){

    var dataBase: MovieDataBase = Room.databaseBuilder(
        application,
        MovieDataBase::class.java,
        "favoriteMovie.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideFavoriteMovieDAO() : FavoriteMovieDAO{
        return dataBase.getFavoriteMovieDAO()
    }

    @Provides
    fun provideMovieDataBase() : MovieDataBase{
        return dataBase
    }
}