package com.example.jokerfinder.features.favoritemovies.favoritemoviesadapter

import androidx.recyclerview.widget.DiffUtil
import com.example.jokerfinder.pojoes.FavoriteMovieEntity

class FavoriteMovieDiffUtils : DiffUtil.ItemCallback<FavoriteMovieEntity>() {
    override fun areItemsTheSame(
        oldItem: FavoriteMovieEntity,
        newItem: FavoriteMovieEntity
    ): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(
        oldItem: FavoriteMovieEntity,
        newItem: FavoriteMovieEntity
    ): Boolean {
        return oldItem.movieId == newItem.movieId
    }
}