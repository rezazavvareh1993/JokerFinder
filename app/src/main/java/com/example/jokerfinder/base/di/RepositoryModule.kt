package com.example.jokerfinder.base.di

import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {

    @Provides
    fun provideRepository(networkRepository: NetworkRepository, favoriteMovieDAO: FavoriteMovieDAO) : DataRepository{
        return DataRepository(networkRepository, favoriteMovieDAO)
    }


}