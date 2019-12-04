package com.example.jokerfinder.features.searchmovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.utils.MyConstantClass
import com.example.jokerfinder.R
import com.example.jokerfinder.features.moviedetails.MovieDetailsActivity
import com.example.jokerfinder.retrofit.RetrofitProvideClass
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_list.*

class SearchMovieActivity : AppCompatActivity(), View.OnClickListener {

    private var imgSearchMovie: ImageView? = null
    private var checkSearchButton = true
    private val disposable = CompositeDisposable()
    private val getIdMovieLambdaFunction: (Int) -> Unit = {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("idMovie", it)
        startActivity(intent)
    }

    private var adapter =
        MoviesAdapter(
            getIdMovieLambdaFunction
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        progressBar_in_movie_list.visibility = View.GONE

        initView()
        setOnClicks()
        setUpRecyclerView()

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

    private fun setUpRecyclerView() {

        movie_recycler_view.setHasFixedSize(true)
        movie_recycler_view.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
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
                    swipeContainer.isRefreshing = false
                    adapter.submitList(it.results)
                }, {
                    Log.d("MyTag", it.message)
                    MyConstantClass.showToast(this, this.resources.getString(R.string.error_connection))
                    progressBar_in_movie_list.visibility = View.GONE
                    swipeContainer.isRefreshing = false
                },{
                    progressBar_in_movie_list.visibility = View.GONE
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_search_movie -> {
                if(checkSearchButton){
                        callGetListMovies()
                        progressBar_in_movie_list.visibility = View.VISIBLE
                        img_search_movie.setImageResource(R.drawable.ic_clear_red_24dp)
                }else{
                    edt_movie_name_search.text.clear()
                    img_search_movie.setImageResource(R.drawable.ic_search_red_24dp)
                }
                checkSearchButton = !checkSearchButton
            }
            else -> {
            }
        }
    }
}
