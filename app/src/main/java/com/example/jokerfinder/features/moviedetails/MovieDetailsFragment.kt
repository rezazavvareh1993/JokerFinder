package com.example.jokerfinder.features.moviedetails


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.base.di.DaggerProvideRepository
import com.example.jokerfinder.features.di.BaseViewModelFactory
import com.example.jokerfinder.features.moviedetails.castsofmovie.CastOfMovieViewModel
import com.example.jokerfinder.features.moviedetails.castsofmovie.castadapter.CastsMovieAdapter
import com.example.jokerfinder.pojoes.Crew
import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.repository.DataRepository
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_movie_details.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : BaseFragment() ,View.OnClickListener{

    @Inject
    lateinit var repository : DataRepository

    lateinit var factory: ViewModelProvider.Factory


    lateinit var navController: NavController

    ///////////////Views
    private lateinit var imgBack : ImageView

    //////////////disposable
    private val disposable = CompositeDisposable()

    /////////////adapter
    private val adapter =
        CastsMovieAdapter()

    /////////////////////viewModels
    private lateinit var movieDetailViewModel : MovieDetailsViewModel
    private lateinit var castOfMovieViewModel: CastOfMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        loadingViews()
        callGetMovieDetails()
        setUpRecyclerView()
        setOnClicks()
    }

    private fun setOnClicks() {
        imgBack.setOnClickListener(this)
    }

    private fun init(view: View) {
        DaggerProvideRepository.create().getMovieDetailsFragment(this)
        navController = Navigation.findNavController(view)
        imgBack = view.findViewById(R.id.img_back_movie_detail)
        factory = BaseViewModelFactory(repository)
        movieDetailViewModel = ViewModelProvider(this, factory).get(MovieDetailsViewModel::class.java)
        castOfMovieViewModel = ViewModelProvider(this, factory).get(CastOfMovieViewModel::class.java)
    }

    private fun setUpRecyclerView() {
        cast_recycler_view.setHasFixedSize(true)
        cast_recycler_view.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        cast_recycler_view.adapter = adapter
    }

    private fun loadingViews() {

        progressBar.visibility = View.VISIBLE
        cv_logo_detail_movie.visibility = View.GONE
        img_main_detail_movie.visibility = View.GONE
        txt_movie_detail_overview.visibility = View.GONE
        txt_movie_detail_title.visibility = View.GONE
        txt_movie_detail_director.visibility = View.GONE
        txt_movie_detail_rate.visibility = View.GONE
        txt_movie_detail_writer.visibility = View.GONE
        txt_movie_detail_producer.visibility = View.GONE

    }

    private fun callGetMovieDetails() {

        movieDetailViewModel.fetchData(getIdMovie(), requireContext())
        movieDetailViewModel.getMovieDetailsData().observe(this, Observer {

            if(it != null){
                bindData(it)
                showViews()
                callGetCastsOfMovie()
            }
            progressBar.visibility  = View.GONE
        })
    }

    private fun callGetCastsOfMovie(){

        castOfMovieViewModel.fetchCastOfMovieData(getIdMovie(), requireContext())
        castOfMovieViewModel.getCastOfMovieData().observe(this, Observer {

            adapter.submitList(it?.cast)
            it?.crew?.let { it1 -> getCrewOfMovie(it1) }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun getCrewOfMovie(crewList: List<Crew>) {
        var writer  = ""
        var director = ""
        var producer = ""
        for(i in crewList){
            when(i.job){
                "Director" -> {
                    director += i.name +  ","
                    txt_movie_detail_director.text = "Director : " + director
                }
                "Writer" -> {
                    writer += i.name + ","
                    txt_movie_detail_writer.text = "Writer : " + writer
                }
                "Producer" -> {
                    producer += i.name + ","
                    txt_movie_detail_producer.text = "Producer : " + producer
                }
            }
        }
    }

    private fun showViews() {

        progressBar.visibility  = View.GONE
        cv_logo_detail_movie.visibility = View.VISIBLE
        img_main_detail_movie.visibility = View.VISIBLE
        txt_movie_detail_overview.visibility = View.VISIBLE
        txt_movie_detail_title.visibility = View.VISIBLE
        txt_movie_detail_director.visibility = View.VISIBLE
        txt_movie_detail_rate.visibility = View.VISIBLE
        txt_movie_detail_writer.visibility = View.VISIBLE
        txt_movie_detail_producer.visibility = View.VISIBLE
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
        val safeArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments())
        return safeArgs.idMovie

    }
    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.img_back_movie_detail ->{
                navController.navigate(R.id.action_movieDetailsFragment_to_searchMovieFragment)
                onDestroy()
            }
        }

    }
}
