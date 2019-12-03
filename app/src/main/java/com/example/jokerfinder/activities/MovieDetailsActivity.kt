package com.example.jokerfinder.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.Utils.MyConstantClass
import com.example.jokerfinder.R
import com.example.jokerfinder.adapters.CastAdapter
import com.example.jokerfinder.models.ResponseDetailMovie
import com.example.jokerfinder.remote.RetrofitProvideClass
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.itm_movie_list.*

class MovieDetailsActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()
    private val adapter = CastAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        loadingViews()
        callGetMovieDetails()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        cast_recycler_view.setHasFixedSize(true)
        cast_recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        cast_recycler_view.adapter = adapter
    }

    private fun loadingViews() {

        progressBar.visibility = View.VISIBLE
        img_detail_movie.visibility = View.GONE
        txt_movie_detail_overview.visibility = View.GONE
        txt_movie_detail_title.visibility = View.GONE
        txt_movie_detail_director.visibility = View.GONE
        txt_movie_detail_rate.visibility = View.GONE
    }

    private fun callGetMovieDetails() {
        disposable.add(
            RetrofitProvideClass.provideRetrofit()
                .getMovieDetails(
                    getIdMovie(),
                    MyConstantClass.APY_KEY
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    bindData(it)
                    showViews()

                }, {
                    Log.d("MyTag", it.message)
                    MyConstantClass.showToast(this, this.resources.getString(R.string.error_connection))
                    progressBar.visibility  = View.GONE
                }, {
                    callGetCastsOfMovie()
                })
        )
    }

    private fun callGetCastsOfMovie(){
        disposable.add(
            RetrofitProvideClass.provideRetrofit()
                .getCastsOfMovies(
                    getIdMovie(),
                    MyConstantClass.APY_KEY
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.submitList(it.cast)
                }, {

                    Log.d("MyTag", it.message)
                    MyConstantClass.showToast(this, this.resources.getString(R.string.error_connection))
                })
        )
    }

    private fun showViews() {

        progressBar.visibility  = View.GONE
        img_detail_movie.visibility = View.VISIBLE
        txt_movie_detail_overview.visibility = View.VISIBLE
        txt_movie_detail_title.visibility = View.VISIBLE
        txt_movie_detail_director.visibility = View.VISIBLE
        txt_movie_detail_rate.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(responseDetailMovie: ResponseDetailMovie) {
        txt_movie_detail_overview.text = "OverView : " + responseDetailMovie.overview
        txt_movie_detail_title.text = responseDetailMovie.originalTitle
        txt_movie_detail_director.text = "Director : "
        txt_movie_detail_rate.text ="Rate :  " + responseDetailMovie.voteAverage.toString()
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + responseDetailMovie.posterPath).into(img_detail_movie)
    }

    private fun getIdMovie(): Int {

        return intent.getIntExtra("idMovie", 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}