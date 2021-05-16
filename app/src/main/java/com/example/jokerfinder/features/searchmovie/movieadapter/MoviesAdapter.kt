package com.example.jokerfinder.features.searchmovie.movieadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.databinding.ItemMovieListBinding
import com.example.jokerfinder.pojo.ResultModel
import com.squareup.picasso.Picasso

class MoviesAdapter(private val getMovieIdFunction: (Int) -> Unit) :
    PagingDataAdapter<ResultModel, MoviesAdapter.ViewHolder>(MovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(

        ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        getMovieIdFunction
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class ViewHolder(
        private val binding: ItemMovieListBinding,
        private val getMovieIdFunction: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(resultModel: ResultModel) {

            val uriImage = "https://image.tmdb.org/t/p/w500" + resultModel.posterPath
            Picasso.get().load(uriImage).into(binding.imgMoviePic)
            with(itemView) {
                binding.txtNameMovie.text = resultModel.title
                binding.txtMovieListRate.text = resultModel.voteAverage.toString()
                binding.txtVoteCountMovie.text = "votes : ${resultModel.voteCount}"
                binding.txtReleasedMovie.text = "released : ${resultModel.releaseDate}"
                binding.ratingBarMovie.rating = resultModel.voteAverage.toFloat() / 2
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