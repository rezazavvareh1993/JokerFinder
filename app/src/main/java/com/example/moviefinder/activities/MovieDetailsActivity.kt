package com.example.moviefinder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.moviefinder.MyConstantClass
import com.example.moviefinder.R
import com.example.moviefinder.models.ResponseDetailMovie
import com.example.moviefinder.remote.RetrofitProvideClass
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        callGetMovieDetails()
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
                    showToast("success")
                    bindData(it)
                }, {
                    showToast(it?.message)
                })
        )
    }

    private fun bindData(responseDetailMovie: ResponseDetailMovie) {
        txt_movie_detail_overview.text = responseDetailMovie.overview
        txt_movie_detail_title.text = responseDetailMovie.originalTitle
        txt_movie_detail_rate.text = responseDetailMovie.voteAverage.toString()
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + responseDetailMovie.backdropPath).into(img_detail_movie)
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
    }

    private fun getIdMovie(): Int {
        return intent.getIntExtra("idMovie", 0)

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}