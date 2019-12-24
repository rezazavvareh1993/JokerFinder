package com.example.jokerfinder.base.di

import android.app.Application
import com.example.jokerfinder.features.favoritemovies.FavoriteMoviesFragment
import com.example.jokerfinder.features.moviedetails.MovieDetailsFragment
import com.example.jokerfinder.features.searchmovie.SearchMovieFragment
import dagger.Component

@Component(modules = [ApplicationModule::class, RoomModule::class, RepositoryModule::class, BaseViewModelFactoryModule::class])
interface ApplicationComponent {

    fun injectToSearchMovieFragment(searchMovieFragment: SearchMovieFragment)
    fun injectToMovieDetailsFragment(movieDetailsFragment : MovieDetailsFragment)
    fun injectToFavoriteMoviesFragment(favoriteMoviesFragment : FavoriteMoviesFragment)

    fun application() : Application

}