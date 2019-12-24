package com.example.jokerfinder.features.favoritemovies


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseApplication
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.base.BaseViewModelFactory
import com.example.jokerfinder.features.favoritemovies.favoritemoviesadapter.FavoriteMoviesAdapter
import com.example.jokerfinder.features.moviedetails.MovieDetailsViewModel
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.utils.MyConstantClass
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMoviesFragment : BaseFragment() {

    //////////////Inject
    @Inject
    lateinit var factory : ViewModelProvider.Factory

    ///////////////navController
    lateinit var navController: NavController

    ///////////////ViewModels
    lateinit var favoriteMovieViewModel: FavoriteMovieViewModel
    lateinit var movieDetailsViewModel: MovieDetailsViewModel

    ////////////////lambdaFunctions
    private val getFavoriteMovieId : (Int) -> Unit = {
        val bundle = bundleOf("movieId" to it)
        navController.navigate(R.id.action_favoriteMovieFragment_to_movieDetailsFragment, bundle)
    }

    /////////////adapter
    private val adapter =
        FavoriteMoviesAdapter(getFavoriteMovieId)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectFactory()
        init(view)
        callGetListFavoriteMovies()
        setUpRecyclerView()
    }

    private fun injectFactory() {
        (activity?.application as BaseApplication)
            .getApplicationComponent()
            .injectToFavoriteMoviesFragment(this)
    }

    private fun setUpRecyclerView() {
        val layoutManager =  LinearLayoutManager(context , RecyclerView.VERTICAL, false)
        favorite_movie_recycler_view.layoutManager = layoutManager
        favorite_movie_recycler_view.adapter = adapter
    }

    private fun callGetListFavoriteMovies() {
        favoriteMovieViewModel.fetchAllFavoriteMovies()
        favoriteMovieViewModel.getAllFavoriteMovies().observe(this, Observer {
            if(it != null)
                adapter.submitList(it)
            else
                MyConstantClass.showToast(requireContext(), context?.resources?.getString(R.string.error_connection))


        })
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        favoriteMovieViewModel = ViewModelProvider(this, factory).get(FavoriteMovieViewModel::class.java)
        movieDetailsViewModel = ViewModelProvider(this, factory).get(MovieDetailsViewModel::class.java)
    }
}
