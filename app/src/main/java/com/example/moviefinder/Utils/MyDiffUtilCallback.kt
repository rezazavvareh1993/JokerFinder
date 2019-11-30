package com.example.moviefinder.Utils

import androidx.recyclerview.widget.DiffUtil
import com.example.moviefinder.models.ResultModel


class MyDiffUtilCallback(private val oldList : List<ResultModel>, private val newList : List<ResultModel>) : DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}