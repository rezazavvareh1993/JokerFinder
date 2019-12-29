package com.example.jokerfinder.features.favoritemovies


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseApplication
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.features.favoritemovies.favoritemoviesadapter.FavoriteMoviesAdapter
import com.example.jokerfinder.features.moviedetails.MovieDetailsViewModel
import com.example.jokerfinder.pojoes.FavoriteMovieEntity
import com.google.android.material.snackbar.Snackbar
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

    /////////////Value
    private lateinit var myContext : Context
    private lateinit var lastFavoriteMovieEntityDeleted: FavoriteMovieEntity

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

        ///////////////////deleteMovie
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                lastFavoriteMovieEntityDeleted = adapter.getRoomAt(viewHolder.adapterPosition)
                favoriteMovieViewModel.deleteMovieFromFavoriteMovies(lastFavoriteMovieEntityDeleted)
                callGetListFavoriteMovies()
                showUndoSnackBar(view)
            }
        }).attachToRecyclerView(favorite_movie_recycler_view)
    }



    private fun showUndoSnackBar(view: View) {

        val snackBar = Snackbar.make(
            view, "وسیله مورد نظر پاک شد. ",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction(
            "Undo"
        ) {
            undoDelete()
        }
            .setActionTextColor(resources.getColor(R.color.colorPrimary))
        snackBar.setActionTextColor(Color.BLUE)
        snackBar.show()

    }

    private fun undoDelete() {
        favoriteMovieViewModel.insertFavoriteMovie(lastFavoriteMovieEntityDeleted)
        callGetListFavoriteMovies()
    }

    private fun injectFactory() {
        (activity?.application as BaseApplication)
            .getApplicationComponent()
            .injectToFavoriteMoviesFragment(this)
    }

    private fun setUpRecyclerView() {
        val layoutManager =  LinearLayoutManager(myContext , RecyclerView.VERTICAL, false)
        favorite_movie_recycler_view.layoutManager = layoutManager
        favorite_movie_recycler_view.adapter = adapter
    }

    private fun callGetListFavoriteMovies() {
        favoriteMovieViewModel.fetchAllFavoriteMovies()
        favoriteMovieViewModel.getAllFavoriteMovies().observe(this as LifecycleOwner, Observer {

            it?.let { adapter.submitList(it) }
            progress_bar_in_favorite_movies_fragment.visibility = View.GONE

        })
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        favoriteMovieViewModel = ViewModelProvider(this, factory).get(FavoriteMovieViewModel::class.java)
        movieDetailsViewModel = ViewModelProvider(this, factory).get(MovieDetailsViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}
