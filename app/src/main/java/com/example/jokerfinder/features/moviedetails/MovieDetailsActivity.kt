package com.example.jokerfinder.features.moviedetails

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.pojoes.Crew
import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {
    //////////////disposable
    private val disposable = CompositeDisposable()
    
    /////////////adapter
    private val adapter = CastsMovieAdapter()
    
    /////////////////////viewModels
    private lateinit var movieDetailViewModel : MovieDetailsViewModel
    private lateinit var castOfMovieViewModel: CastOfMovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        init()
        loadingViews()
        callGetMovieDetails()
        setUpRecyclerView()

    }

    private fun init() {
        movieDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MovieDetailsViewModel::class.java)
        castOfMovieViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(CastOfMovieViewModel::class.java)
    }

    private fun setUpRecyclerView() {
        cast_recycler_view.setHasFixedSize(true)
        cast_recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        cast_recycler_view.adapter = adapter
    }

    private fun loadingViews() {

        progressBar.visibility = VISIBLE
        cv_logo_detail_movie.visibility = GONE
        img_main_detail_movie.visibility = GONE
        txt_movie_detail_overview.visibility = GONE
        txt_movie_detail_title.visibility = GONE
        txt_movie_detail_director.visibility = GONE
        txt_movie_detail_rate.visibility = GONE
        txt_movie_detail_writer.visibility = GONE
        txt_movie_detail_producer.visibility = GONE

    }

    private fun callGetMovieDetails() {

        movieDetailViewModel.fetchData(getIdMovie(), this)
        movieDetailViewModel.getMovieDetailsData().observe(this, Observer {

            if(it != null){
                bindData(it)
                showViews()
                callGetCastsOfMovie()
            }
            progressBar.visibility  = GONE
        })
    }

    private fun callGetCastsOfMovie(){
        
        castOfMovieViewModel.fetchCastOfMovieData(getIdMovie(), this)
        castOfMovieViewModel.getCastOfMovieData().observe(this, Observer {
            
            adapter.submitList(it?.cast)
            it?.crew?.let { it1 -> getCrewOfMovie(it1) }
        })
        
//        disposable.add(
//            RetrofitProvideClass.provideRetrofit()
//                .getCastsOfMovie(
//                    getIdMovie(),
//                    MyConstantClass.APY_KEY
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    adapter.submitList(it.cast)
//                    getProductsMovie(it.crew)
//                }, {
//
//                    Log.d("MyTag", it.message)
//                    MyConstantClass.showToast(this, this.resources.getString(R.string.error_connection))
//                })
//        )
    }

    @SuppressLint("SetTextI18n")
    private fun getCrewOfMovie(crewList: List<Crew>) {

        for(i in crewList){
            when(i.job){
                "Director" -> txt_movie_detail_director.text = "Director : " + i.name
                "Writer" -> txt_movie_detail_writer.text = "Writer : " + i.name
                "Producer" -> txt_movie_detail_producer.text = "Producer : " + i.name
            }
        }
    }

    private fun showViews() {

        progressBar.visibility  = GONE
        cv_logo_detail_movie.visibility = VISIBLE
        img_main_detail_movie.visibility = VISIBLE
        txt_movie_detail_overview.visibility = VISIBLE
        txt_movie_detail_title.visibility = VISIBLE
        txt_movie_detail_director.visibility = VISIBLE
        txt_movie_detail_rate.visibility = VISIBLE
        txt_movie_detail_writer.visibility = VISIBLE
        txt_movie_detail_producer.visibility = VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(responseDetailMovie: ResponseDetailMovie) {

        txt_movie_detail_overview.text = responseDetailMovie.overview
        txt_movie_detail_title.text = responseDetailMovie.originalTitle
        txt_movie_detail_rate.text ="Rate :  " + responseDetailMovie.voteAverage.toString()
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + responseDetailMovie.backdropPath).into(img_main_detail_movie)
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + responseDetailMovie.posterPath).into(img_logo_detail_movie)

    }

    private fun getIdMovie(): Int {
        return intent.getIntExtra("idMovie", 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}