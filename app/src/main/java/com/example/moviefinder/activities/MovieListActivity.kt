package com.example.moviefinder.activities

import android.content.Intent
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
    private val getIdMovieLambdaFunction: (Int) -> Unit = {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("idMovie", it)
        showToast(it.toString())
        startActivity(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        setOnClicks()

        swipeContainer.setOnRefreshListener {

            callGetListMovies()
        }
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
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

    private fun callGetListMovies() {
        disposable.add(
            RetrofitProvideClass.provideRetrofit()
                .getMovieDetailSearched(
                    MyConstantClass.APY_KEY,
                    edt_movie_name_search.text.toString()
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showToast("success")
                    adapter = MovieAdapter(it.results,getIdMovieLambdaFunction)
                    setUpRecyclerView(adapter)
                    swipeContainer.isRefreshing = false
                }, {
                    showToast(it?.message)
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_search_movie -> callGetListMovies()
            else -> {}
        }
    }

    private fun showToast(myMessage : String?){
        Toast.makeText(this, myMessage, Toast.LENGTH_LONG).show()

    }
}
