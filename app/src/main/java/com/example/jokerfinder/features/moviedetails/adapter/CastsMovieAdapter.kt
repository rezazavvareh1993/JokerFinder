package com.example.jokerfinder.features.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.databinding.ItemCastsListBinding
import com.example.jokerfinder.pojo.Cast
import com.squareup.picasso.Picasso

class CastsMovieAdapter : ListAdapter<Cast, CastsMovieAdapter.CastViewHolder>(
    CastDiffUtilCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            ItemCastsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {

        val position = holder.layoutPosition
        holder.bind(getItem(position))
    }

    class CastViewHolder(val binding: ItemCastsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: Cast) {
            val uriImage = "https://image.tmdb.org/t/p/w500${cast.profilePath}"
            Picasso.get().load(uriImage).into(binding.imgCastPic)
            binding.txtCharacterCast.text = "Character : ${cast.character}"
            binding.txtNameCast.text = "Name : ${cast.name}"
        }
    }

    class CastDiffUtilCallBack : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast) =
            oldItem.castId == newItem.castId

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast) =
            oldItem.castId == newItem.castId
    }
}