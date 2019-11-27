package com.example.moviefinder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefinder.MyConstantClass
import com.example.moviefinder.R
import com.example.moviefinder.remote.RetrofitProvideClass
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.calendar.kotlincource.KotlinCodes.KotlinRecyclerView.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MovieListActivity : AppCompatActivity(), View.OnClickListener {

    private var imgSearchMovie: ImageView? = null
    lateinit var adapter: MovieAdapter
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        setOnClicks()
    }

    private fun initView() {
        imgSearchMovie = findViewById(R.id.img_search_movie)
    }

    private fun setOnClicks() {
        imgSearchMovie?.setOnClickListener(this)
    }

    private fun setUpRecyclerView(adapter: MovieAdapter) {

        movie_recycler_view.setHasFixedSize(true)
        movie_recycler_view.layoutManager = LinearLayoutManager(this)
        movie_recycler_view.adapter = adapter
    }

    private fun callRetrofit() {
        disposable.add(
            RetrofitProvideClass.provideRetrofit()
                .getMovieDetailSearched(
                    MyConstantClass.APY_KEY,
                    edt_movie_name_search.text.toString()
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
                    adapter = MovieAdapter(it.results)
                    setUpRecyclerView(adapter)
                }, {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_search_movie -> callRetrofit()
            else -> {}
        }
    }
}
