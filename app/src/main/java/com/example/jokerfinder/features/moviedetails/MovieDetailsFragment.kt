package com.example.jokerfinder.features.moviedetails


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseApplication
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.features.favoritemovies.FavoriteMovieViewModel
import com.example.jokerfinder.features.moviedetails.castsofmovie.CastOfMovieViewModel
import com.example.jokerfinder.features.moviedetails.castsofmovie.castadapter.CastsMovieAdapter
import com.example.jokerfinder.pojoes.Crew
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.example.jokerfinder.pojoes.ResponseDetailMovie
import com.example.jokerfinder.utils.MyConstantClass
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : BaseFragment() ,View.OnClickListener{


    ////////////Inject
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    ///////////////////navController
    lateinit var navController: NavController

    ///////////////Views
    private lateinit var imgBack : ImageView

    /////////////adapter
    private val adapter =
        CastsMovieAdapter()

    /////////////values
    private var isLikeMovie = false
    private lateinit var favoriteMovieEntity: FavoriteMovieEntity
    private lateinit var myContext: Context

    /////////////////////viewModels
    private lateinit var favoriteMovieViewModel: FavoriteMovieViewModel
    private lateinit var movieDetailViewModel : MovieDetailsViewModel
    private lateinit var castOfMovieViewModel: CastOfMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectFactory()
        init(view)
        loadingViews()
        callMovieDetails()
        isMovieInDataBase()
        setUpRecyclerView()
        setOnClicks()
    }

    private fun injectFactory() {
        (activity?.application as BaseApplication)
            .getApplicationComponent()
            .injectToMovieDetailsFragment(this)
    }

    private fun setOnClicks() {
        imgBack.setOnClickListener(this)
        img_like_movie_in_details_movie_fragment.setOnClickListener(this)
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        imgBack = view.findViewById(R.id.img_back_movie_detail)
        favoriteMovieViewModel = ViewModelProvider(this, factory).get(FavoriteMovieViewModel::class.java)
        movieDetailViewModel = ViewModelProvider(this, factory).get(MovieDetailsViewModel::class.java)
        castOfMovieViewModel = ViewModelProvider(this, factory).get(CastOfMovieViewModel::class.java)
    }

    private fun setUpRecyclerView() {
        cast_recycler_view.setHasFixedSize(true)
        cast_recycler_view.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        cast_recycler_view.adapter = adapter
    }

    private fun loadingViews() {

        progress_bar_in_movie_details_fragment_for_Details.visibility = View.VISIBLE
        img_like_movie_in_details_movie_fragment.visibility = View.GONE
        cv_logo_detail_movie.visibility = View.GONE
        img_main_detail_movie.visibility = View.GONE
        txt_movie_detail_overview.visibility = View.GONE
        txt_movie_detail_title.visibility = View.GONE
        txt_movie_detail_director.visibility = View.GONE
        txt_movie_detail_rate.visibility = View.GONE
        txt_movie_detail_writer.visibility = View.GONE
        txt_movie_detail_producer.visibility = View.GONE

    }

    private fun callMovieDetails() {

        movieDetailViewModel.fetchMovieDetails(getMovieSearchedId(), myContext)
        movieDetailViewModel.getMovieDetailsData().observe(this as LifecycleOwner, Observer {

            if(it != null){
                saveMovieInformationForUsingDataBase(it)
                bindData(it)
                showViews()
                callCastsOfMovie()
            }
            progress_bar_in_movie_details_fragment_for_Details.visibility  = View.GONE
        })
    }

    private fun saveMovieInformationForUsingDataBase(it: ResponseDetailMovie) {
        favoriteMovieEntity = FavoriteMovieEntity(
            it.id,
            it.title,
            it.releaseDate,
            it.voteAverage,
            it.posterPath
        )
    }

    private fun callCastsOfMovie(){

        castOfMovieViewModel.fetchCastOfMovieData(getMovieSearchedId(), requireContext())
        castOfMovieViewModel.getCastOfMovieData().observe(this as LifecycleOwner, Observer {
            if(it != null){
                adapter.submitList(it.cast)
                getCrewOfMovie(it.crew)
            }
            progress_bar_in_movie_details_fragment_for_stars.visibility = View.GONE

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
                    txt_movie_detail_director.text = "Director : $director"
                }
                "Writer" -> {
                    writer += i.name + ","
                    txt_movie_detail_writer.text = "Writer : $writer"
                }
                "Producer" -> {
                    producer += i.name + ","
                    txt_movie_detail_producer.text = "Producer : $producer"
                }
            }
        }
    }

    private fun showViews() {

        progress_bar_in_movie_details_fragment_for_Details.visibility  = View.GONE
        img_like_movie_in_details_movie_fragment.visibility = View.VISIBLE
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


    private fun getMovieSearchedId(): Int {
        val safeArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments())
        return safeArgs.movieId

    }

    private fun isMovieInDataBase(){
        favoriteMovieViewModel.findMovieByMovieId(getMovieSearchedId())
        favoriteMovieViewModel.getIsMovieInDataBase().observe(this as LifecycleOwner, Observer {
            if(it)
                img_like_movie_in_details_movie_fragment.setImageResource(R.drawable.ic_favorite_red_24dp)
            else
                img_like_movie_in_details_movie_fragment.setImageResource(R.drawable.ic_favorite_border_red_24dp)
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.img_back_movie_detail -> {
                navController.navigate(R.id.action_movieDetailsFragment_to_searchMovieFragment)
                onDestroy()
            }
            R.id.img_like_movie_in_details_movie_fragment -> {
                isLikeMovie = !isLikeMovie

                if(isLikeMovie){
                    favoriteMovieViewModel.insertFavoriteMovie(favoriteMovieEntity)
                    img_like_movie_in_details_movie_fragment.setImageResource(R.drawable.ic_favorite_red_24dp)
                }else{
                    favoriteMovieViewModel.deleteMovieFromFavoriteMovies(favoriteMovieEntity)
                    img_like_movie_in_details_movie_fragment.setImageResource(R.drawable.ic_favorite_border_red_24dp)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}
