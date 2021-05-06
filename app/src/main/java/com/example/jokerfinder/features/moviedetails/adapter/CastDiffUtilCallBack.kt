package com.example.jokerfinder.features.moviedetails.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.jokerfinder.pojo.Cast

class CastDiffUtilCallBack : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.castId == newItem.castId
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.castId == newItem.castId
    }
}