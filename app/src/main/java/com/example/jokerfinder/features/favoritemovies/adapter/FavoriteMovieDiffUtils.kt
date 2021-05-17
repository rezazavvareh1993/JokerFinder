package com.example.jokerfinder.features.favoritemovies.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.jokerfinder.base.db.FavoriteMovieEntity

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