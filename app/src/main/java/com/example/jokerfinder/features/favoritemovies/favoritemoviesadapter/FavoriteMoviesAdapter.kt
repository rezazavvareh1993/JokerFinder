package com.example.jokerfinder.features.favoritemovies.favoritemoviesadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_favorite_movie.view.*

class FavoriteMoviesAdapter(private val getFavoriteMovieId : (Int) -> (Unit)) : ListAdapter<FavoriteMovieEntity, FavoriteMoviesAdapter.FavoriteMovieViewHolder>(FavoriteMovieDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_movie, parent, false)
        return FavoriteMovieViewHolder(view, getFavoriteMovieId)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val position = holder.layoutPosition
        holder.bind(getItem(position))
    }

    class FavoriteMovieViewHolder(itemView : View, private val getFavoriteMovieId : (Int) -> (Unit)) : RecyclerView.ViewHolder(itemView) {

        fun bind (favoriteMovieEntity: FavoriteMovieEntity){

            val uriImage = "https://image.tmdb.org/t/p/w500" + favoriteMovieEntity.moviePicUrl
            getImageMovieByPicasso(uriImage)

            itemView.txt_name_favorite_name.text = favoriteMovieEntity.movieName
            itemView.txt_release_date_favorite_movie.text = favoriteMovieEntity.movieReleaseDate
            itemView.txt_rate_favorite_movie.text = favoriteMovieEntity.movieRate.toString()

            itemView.setOnClickListener {
                getFavoriteMovieId(favoriteMovieEntity.movieId)
            }
        }


        private fun getImageMovieByPicasso(uriImage: String) {
            Picasso
                .get()
                .load(uriImage)
                .fit()
                .into(itemView.img_background_favorite_movie)
        }
    }
}