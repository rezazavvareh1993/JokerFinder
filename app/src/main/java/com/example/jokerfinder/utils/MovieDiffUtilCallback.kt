package com.example.jokerfinder.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.jokerfinder.pojoes.ResultModel


class MovieDiffUtilCallback : DiffUtil.ItemCallback<ResultModel>() {
    override fun areItemsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
        return oldItem.id == newItem.id
    }

}