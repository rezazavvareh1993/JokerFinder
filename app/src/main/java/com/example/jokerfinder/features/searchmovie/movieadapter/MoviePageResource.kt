package com.example.jokerfinder.features.searchmovie.movieadapter

import androidx.paging.PagingSource
import com.example.jokerfinder.pojo.ResultModel
import com.example.jokerfinder.repository.DataRepository

class MoviePageResource(
    private val repository: DataRepository,
    private val movieName: String
) : PagingSource<Int, ResultModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultModel> =
        try {
            val page = params.key ?: 1
            val response = repository.fetchMovieSearchData(movieName, page)
            val previousPage = if (page - 1 > 0) page - 1 else null
            val nextPage = if (response.results.isNullOrEmpty()) null else page + 1
            LoadResult.Page(response.results, previousPage, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}