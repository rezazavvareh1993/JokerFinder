package com.example.jokerfinder.features.moviedetails


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.features.favoritemovies.FavoriteMovieViewModel
import com.example.jokerfinder.features.moviedetails.adapter.CastsMovieAdapter
import com.example.jokerfinder.pojo.Crew
import com.example.jokerfinder.base.db.FavoriteMovieEntity
import com.example.jokerfinder.pojo.ResponseDetailMovie
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment(), View.OnClickListener {

    private val movieDetailViewModel: MovieDetailsViewModel by viewModels()
    private val favoriteMovieViewModel: FavoriteMovieViewModel by activityViewModels()
    private var isLikeMovie = false
    private lateinit var navController: NavController
    private lateinit var adapter: CastsMovieAdapter
    private lateinit var favoriteMovieEntity: FavoriteMovieEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navController = Navigation.findNavController(requireView())
        loadingViews()
        callMovieDetails()
        isMovieInDataBase()
        setUpRecyclerView()
        setOnClicks()
    }

    private fun setOnClicks() {
        imgBack.setOnClickListener(this)
        imgLikeMovie.setOnClickListener(this)
    }

    private fun setUpRecyclerView() {
        rcyCast.setHasFixedSize(true)
        rcyCast.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        adapter = CastsMovieAdapter()
        rcyCast.adapter = adapter
    }

    private fun loadingViews() {

        pbrMovieDetails.visibility = View.VISIBLE
        imgLikeMovie.visibility = View.GONE
        cardMovieLogo.visibility = View.GONE
        imgMainMovie.visibility = View.GONE
        txtOverview.visibility = View.GONE
        txtTitle.visibility = View.GONE
        txtDirector.visibility = View.GONE
        txtRate.visibility = View.GONE
        txtWriter.visibility = View.GONE
        txtProducer.visibility = View.GONE
    }

    private fun callMovieDetails() {
        movieDetailViewModel.fetchMovieDetails(getMovieSearchedId())
        movieDetailViewModel.getMovieDetailsData().observe(this as LifecycleOwner, {
            it?.let {
                saveMovieInformationForUsingDataBase(it)
                bindData(it)
                showViews()
                callCastsOfMovie()
            }
            pbrMovieDetails.visibility = View.GONE
        })
    }

    private fun saveMovieInformationForUsingDataBase(it: ResponseDetailMovie) {
        favoriteMovieEntity = FavoriteMovieEntity(
            it.id, it.title, it.releaseDate, it.voteAverage, it.posterPath
        )
    }

    private fun callCastsOfMovie() {
        movieDetailViewModel.fetchCastOfMovieData(getMovieSearchedId())
        movieDetailViewModel.getCastOfMovieData().observe(this as LifecycleOwner, {
            it?.let { credits ->
                adapter.submitList(it.cast)
                credits.crew?.let { crew -> getCrewOfMovie(crew) }
            }
            pbrCast.visibility = View.GONE
        })
    }

    @SuppressLint("SetTextI18n")
    private fun getCrewOfMovie(crewList: List<Crew>) {
        var writer = ""
        var director = ""
        var producer = ""
        for (i in crewList) {
            when (i.job) {
                "Director" -> {
                    director += i.name + ","
                    txtDirector.text = "Director : $director"
                }
                "Writer" -> {
                    writer += i.name + ","
                    txtWriter.text = "Writer : $writer"
                }
                "Producer" -> {
                    producer += i.name + ","
                    txtProducer.text = "Producer : $producer"
                }
            }
        }
    }

    private fun showViews() {

        pbrMovieDetails.visibility = View.GONE
        imgLikeMovie.visibility = View.VISIBLE
        cardMovieLogo.visibility = View.VISIBLE
        imgMainMovie.visibility = View.VISIBLE
        txtOverview.visibility = View.VISIBLE
        txtTitle.visibility = View.VISIBLE
        txtDirector.visibility = View.VISIBLE
        txtRate.visibility = View.VISIBLE
        txtWriter.visibility = View.VISIBLE
        txtProducer.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(responseDetailMovie: ResponseDetailMovie) {

        txtOverview.text = responseDetailMovie.overview
        txtTitle.text = responseDetailMovie.originalTitle
        txtRate.text = "Rate :  " + responseDetailMovie.voteAverage.toString()
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + responseDetailMovie.backdropPath)
            .into(imgMainMovie)
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + responseDetailMovie.posterPath)
            .into(img_logo_detail_movie)

    }


    private fun getMovieSearchedId(): Int {
        val safeArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments())
        return safeArgs.movieId
    }

    private fun isMovieInDataBase() {
        favoriteMovieViewModel.findMovieByMovieId(getMovieSearchedId())
        favoriteMovieViewModel.getIsMovieInDataBase().observe(this as LifecycleOwner, {
            if (it)
                imgLikeMovie.setImageResource(R.drawable.ic_favorite_red)
            else
                imgLikeMovie.setImageResource(R.drawable.ic_favorite_border)
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgBack -> {
                findNavController().navigateUp()
            }
            R.id.imgLikeMovie -> {
                isLikeMovie = !isLikeMovie
                if (isLikeMovie) {
                    favoriteMovieViewModel.insertFavoriteMovie(favoriteMovieEntity)
                    imgLikeMovie.setImageResource(R.drawable.ic_favorite_red)
                } else {
                    favoriteMovieViewModel.deleteMovieFromFavoriteMovies(favoriteMovieEntity)
                    imgLikeMovie.setImageResource(R.drawable.ic_favorite_border)
                }
            }
        }
    }
}
