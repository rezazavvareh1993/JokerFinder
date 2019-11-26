package com.example.moviefinder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefinder.R
import ir.calendar.kotlincource.KotlinCodes.KotlinRecyclerView.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<String>()
        list.add("reza")
        list.add("reza")
        list.add("reza")
        list.add("reza")
        list.add("reza")
        list.add("reza")

        setUpRecyclerView(list)
    }

    private fun setUpRecyclerView(movieList : List<String>) {
        val adapter = MovieAdapter(movieList)
        movie_recycler_view.layoutManager = LinearLayoutManager(this)
        movie_recycler_view.adapter = adapter
    }
}
