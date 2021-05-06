package com.example.jokerfinder.features.moviedetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.pojo.Cast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_casts_list.view.*

class CastsMovieAdapter : ListAdapter<Cast, CastsMovieAdapter.CastViewHolder>(
    CastDiffUtilCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_casts_list, parent, false)
        return CastViewHolder(
            v
        )
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {

        val position = holder.layoutPosition
        holder.bind(getItem(position))
    }
    class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cast : Cast){
            val uriImage = "https://image.tmdb.org/t/p/w500${cast.profilePath}"
            getImageMovieByPicasso(uriImage)

            itemView.txt_character_cast.text = "Character : ${cast.character}"
            itemView.txt_name_cast.text = "Name : ${cast.name}"
        }

        private fun getImageMovieByPicasso(uriImage: String) {

            Picasso.get().load(uriImage).into(itemView.img_cast_pic)
        }

    }
}