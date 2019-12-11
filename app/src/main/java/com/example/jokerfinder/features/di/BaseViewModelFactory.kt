package com.example.jokerfinder.features.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jokerfinder.features.moviedetails.MovieDetailsViewModel
import com.example.jokerfinder.features.moviedetails.castsofmovie.CastOfMovieViewModel
import com.example.jokerfinder.features.searchmovie.SearchMovieViewModel
import com.example.jokerfinder.repository.DataRepository
import java.lang.IllegalArgumentException

class BaseViewModelFactory (val dataRepository: DataRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchMovieViewModel::class.java) -> SearchMovieViewModel(
                dataRepository
            ) as T
            modelClass.isAssignableFrom(MovieDetailsViewModel::class.java) -> MovieDetailsViewModel(
                dataRepository
            ) as T
            modelClass.isAssignableFrom(CastOfMovieViewModel::class.java) -> CastOfMovieViewModel(
                dataRepository
            ) as T
            else -> throw IllegalArgumentException("No such ViewModel class ${modelClass.name}")
        }
    }
}