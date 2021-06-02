package com.example.jokerfinder.base.di

import android.content.Context
import com.example.jokerfinder.base.db.MovieDB
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import com.example.jokerfinder.repository.networkreopsitory.retrofit.JokerFinderApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDataBase(@ApplicationContext appContext: Context) = MovieDB.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideFavoriteMovieDAO(movieDB: MovieDB) = movieDB.favoriteMovieDAO()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: NetworkRepository, localDataSource: FavoriteMovieDAO) =
        DataRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(jokerFinderApiService: JokerFinderApiService) =
        NetworkRepository(jokerFinderApiService)

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideApiService(retrofit: Retrofit): JokerFinderApiService =
        retrofit.create(JokerFinderApiService::class.java)
}