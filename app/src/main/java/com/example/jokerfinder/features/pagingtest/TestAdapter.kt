package com.example.jokerfinder.features.pagingtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.pojoes.ResultModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_list.view.*

class TestAdapter(private val getMovieIdFunction : (Int) -> Unit)  : PagedListAdapter<ResultModel, TestAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<ResultModel>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(
                oldConcert: ResultModel,
                newConcert: ResultModel
            ) = oldConcert.id == newConcert.id

            override fun areContentsTheSame(
                oldConcert: ResultModel,
                newConcert: ResultModel
            ) = oldConcert.id == newConcert.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return ViewHolder(
            v,
            getMovieIdFunction
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val position = holder.layoutPosition
        holder.bind(getItem(position)!!)
    }

    class ViewHolder(itemView: View, private val getMovieIdFunction : (Int) -> Unit) : RecyclerView.ViewHolder(itemView){

        fun bind (resultModel: ResultModel){

            val uriImage = "https://image.tmdb.org/t/p/w500" + resultModel.posterPath
            getImageMovieByPicasso(uriImage)

            itemView.txt_name_movie.text = resultModel.title
            itemView.txt_movie_list_rate.text = resultModel.voteAverage.toString()
            itemView.txt_vote_count_movie.text = "votes : ${resultModel.voteCount}"
            itemView.txt_released_movie.text = "released : ${resultModel.releaseDate}"
            itemView.ratingBar_movie.rating = resultModel.voteAverage.toFloat()/2

            itemView.setOnClickListener {
                getMovieIdFunction(resultModel.id)
            }



        }

        private fun getImageMovieByPicasso(uriImage: String) {
            Picasso.get().load(uriImage).into(itemView.img_movie_pic)
        }
    }
}