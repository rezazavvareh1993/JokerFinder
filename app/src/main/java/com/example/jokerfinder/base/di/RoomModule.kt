package com.example.jokerfinder.base.di

import android.content.Context
import com.example.jokerfinder.repository.localrepository.FavoriteDBRepository
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO
import com.example.jokerfinder.utils.MovieDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
object DBModule {

    @Provides
    fun provideFavoriteMovieDAO(@ApplicationContext appContext: Context) : FavoriteMovieDAO{
        return MovieDB.getInstance(appContext).favoriteMovieDAO
    }

    @Provides
    fun provideMovieDataBaseRepository(favoriteMovieDAO: FavoriteMovieDAO) = FavoriteDBRepository(favoriteMovieDAO)
}