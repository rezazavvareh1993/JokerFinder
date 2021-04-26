package com.example.jokerfinder.base.di

import android.content.Context
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import com.example.jokerfinder.retrofit.JokerFinderApiService
import com.example.jokerfinder.utils.MovieDB
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiService(retrofit: Retrofit): JokerFinderApiService =
        retrofit.create(JokerFinderApiService::class.java)
}