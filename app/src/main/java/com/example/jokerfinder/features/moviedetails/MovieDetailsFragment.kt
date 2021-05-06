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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.base.db.FavoriteMovieEntity
import com.example.jokerfinder.base.extensions.makeGone
import com.example.jokerfinder.base.extensions.makeVisible
import com.example.jokerfinder.databinding.FragmentMovieDetailsBinding
import com.example.jokerfinder.features.favoritemovies.FavoriteMovieViewModel
import com.example.jokerfinder.features.moviedetails.adapter.CastsMovieAdapter
import com.example.jokerfinder.pojo.Crew
import com.example.jokerfinder.pojo.ResponseDetailMovie
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*

/**
 * A simple [Fragment] subclass for see details of a movie and actors
 */
@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment(), View.OnClickListener {
    private var _binding: FragmentMovieDetailsBinding? = null

    private val binding get() = _binding!!
    private val movieDetailViewModel: MovieDetailsViewModel by viewModels()
    private val favoriteMovieViewModel: FavoriteMovieViewModel by activityViewModels()
    private var isLikeMovie = false
    private lateinit var adapter: CastsMovieAdapter
    private lateinit var favoriteMovieEntity: FavoriteMovieEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingViews()
        callMovieDetails()
        isMovieInDataBase()
        setUpRecyclerView()
        setOnClicks()
    }

    private fun setOnClicks() {
        binding.imgBack.setOnClickListener(this)
        binding.imgLikeMovie.setOnClickListener(this)
    }

    private fun setUpRecyclerView() {
        binding.rcyCast.setHasFixedSize(true)
        binding.rcyCast.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        adapter = CastsMovieAdapter()
        binding.rcyCast.adapter = adapter
    }

    private fun loadingViews() {

        binding.pbrMovieDetails.makeVisible()
        binding.imgLikeMovie.makeGone()
        binding.cardMovieLogo.makeGone()
        binding.imgMainMovie.makeGone()
        binding.txtOverview.makeGone()
        binding.txtTitle.makeGone()
        binding.txtDirector.makeGone()
        binding.txtRate.makeGone()
        binding.txtWriter.makeGone()
        binding.txtProducer.makeGone()
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
            binding.pbrMovieDetails.makeGone()
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
            pbrCast.makeGone()
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

        binding.pbrMovieDetails.makeGone()
        binding.imgLikeMovie.makeVisible()
        binding.cardMovieLogo.makeVisible()
        binding.imgMainMovie.makeVisible()
        binding.txtOverview.makeVisible()
        binding.txtTitle.makeVisible()
        binding.txtDirector.makeVisible()
        binding.txtRate.makeVisible()
        binding.txtWriter.makeVisible()
        binding.txtProducer.makeVisible()
    }

    private fun bindData(responseDetailMovie: ResponseDetailMovie) {
        binding.txtOverview.text = responseDetailMovie.overview
        binding.txtTitle.text = responseDetailMovie.originalTitle
        binding.txtRate.text = "Rate :  ${responseDetailMovie.voteAverage}"
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
                binding.imgLikeMovie.setImageResource(R.drawable.ic_favorite_red)
            else
                binding.imgLikeMovie.setImageResource(R.drawable.ic_favorite_border)
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
                    binding.imgLikeMovie.setImageResource(R.drawable.ic_favorite_red)
                } else {
                    favoriteMovieViewModel.deleteMovieFromFavoriteMovies(favoriteMovieEntity)
                    binding.imgLikeMovie.setImageResource(R.drawable.ic_favorite_border)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
