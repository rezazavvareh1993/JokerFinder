package com.example.moviefinder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefinder.R
import ir.calendar.kotlincource.KotlinCodes.KotlinRecyclerView.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MovieListActivity : AppCompatActivity() {

    val dataList : MutableList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         initData()

        val adapter = MovieAdapter(dataList)
        movie_recycler_view.adapter = adapter

        setUpRecyclerView()

        //////////////////////////Event
        btn_insert_data.setOnClickListener {
            val newdata = ArrayList<String>()
            for(i in 0..2)
                newdata.add(UUID.randomUUID().toString())

            adapter.insertItem(newdata)
            movie_recycler_view.smoothScrollToPosition(adapter.itemCount-1)//Auto scroll to last item

        }


        btn_update_data.setOnClickListener {
            val newdata = ArrayList<String>()
            for(i in 0..2)
                newdata.add(UUID.randomUUID().toString())

            adapter.updateItem(newdata)
            movie_recycler_view.smoothScrollToPosition(adapter.itemCount-1)//Auto scroll to last item
        }
        }



    private fun initData() {
        for(i in 0..1)
            dataList.add(UUID.randomUUID().toString())

    }



    private fun setUpRecyclerView() {

        movie_recycler_view.setHasFixedSize(true)

        movie_recycler_view.layoutManager = LinearLayoutManager(this)

    }
}
