package com.example.jokerfinder.features.searchmovie.movieadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.pojo.ResultModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MoviesAdapter(private val getMovieIdFunction: (Int) -> Unit) :
    PagingDataAdapter<ResultModel, MoviesAdapter.ViewHolder>(MovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false),
        getMovieIdFunction
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class ViewHolder(itemView: View, private val getMovieIdFunction: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(resultModel: ResultModel) {

            val uriImage = "https://image.tmdb.org/t/p/w500" + resultModel.posterPath
            Picasso.get().load(uriImage).into(itemView.img_movie_pic)
            with(itemView) {
                txt_name_movie.text = resultModel.title
                txt_movie_list_rate.text = resultModel.voteAverage.toString()
                txt_vote_count_movie.text = "votes : ${resultModel.voteCount}"
                txt_released_movie.text = "released : ${resultModel.releaseDate}"
                ratingBar_movie.rating = resultModel.voteAverage.toFloat() / 2
                setOnClickListener { getMovieIdFunction(resultModel.id) }
            }
        }
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<ResultModel>() {
        override fun areItemsTheSame(oldItem: ResultModel, newItem: ResultModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ResultModel, newItem: ResultModel) =
            oldItem.id == newItem.id
    }
}