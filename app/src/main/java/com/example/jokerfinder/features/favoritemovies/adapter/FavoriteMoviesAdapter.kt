package com.example.jokerfinder.features.favoritemovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.base.db.FavoriteMovieEntity
import com.example.jokerfinder.databinding.ItemFavoriteMovieBinding
import com.squareup.picasso.Picasso

class FavoriteMoviesAdapter(private val getFavoriteMovieId : (Int) -> (Unit)) : ListAdapter<FavoriteMovieEntity, FavoriteMoviesAdapter.FavoriteMovieViewHolder>(FavoriteMovieDiffUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val binding =
            ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(binding, getFavoriteMovieId)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val position = holder.layoutPosition
        holder.bind(getItem(position))
    }

    fun getRoomAt(position: Int): FavoriteMovieEntity {
        return getItem(position)
    }

    class FavoriteMovieViewHolder(
        private val binding: ItemFavoriteMovieBinding,
        private val getFavoriteMovieId: (Int) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteMovieEntity: FavoriteMovieEntity) {

            val uriImage = "https://image.tmdb.org/t/p/w500" + favoriteMovieEntity.moviePicUrl
            getImageMovieByPicasso(uriImage)

            binding.txtNameFavoriteName.text = favoriteMovieEntity.movieName
            binding.txtReleaseDateFavoriteMovie.text = favoriteMovieEntity.movieReleaseDate
            binding.txtRateFavoriteMovie.text = favoriteMovieEntity.movieRate.toString()

            itemView.setOnClickListener {
                getFavoriteMovieId(favoriteMovieEntity.movieId)
            }
        }


        private fun getImageMovieByPicasso(uriImage: String) {
            Picasso
                .get()
                .load(uriImage)
                .fit()
                .into(binding.imgBackgroundFavoriteMovie)
        }
    }
}