package com.example.jokerfinder.features.favoritemovies


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.base.db.FavoriteMovieEntity
import com.example.jokerfinder.features.favoritemovies.adapter.FavoriteMoviesAdapter
import com.example.jokerfinder.features.moviedetails.MovieDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_movie.*

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class FavoriteMoviesFragment : BaseFragment() {

    private val favoriteMovieViewModel: FavoriteMovieViewModel by viewModels()
    private val movieDetailsViewModel: MovieDetailsViewModel by activityViewModels()
    private lateinit var lastFavoriteMovieEntityDeleted: FavoriteMovieEntity
    private lateinit var adapter: FavoriteMoviesAdapter
    private lateinit var navController: NavController
    private val getFavoriteMovieId: (Int) -> Unit = {
        val bundle = bundleOf("movieId" to it)
        navController.navigate(R.id.action_favoriteMovieFragment_to_movieDetailsFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(requireView())
        callGetListFavoriteMovies()
        setUpRecyclerView()
        itemTouchHelper()
    }

    private fun itemTouchHelper() {
        ///////////////////deleteMovie
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                lastFavoriteMovieEntityDeleted = adapter.getRoomAt(viewHolder.adapterPosition)
                favoriteMovieViewModel.deleteMovieFromFavoriteMovies(
                    lastFavoriteMovieEntityDeleted
                )
                callGetListFavoriteMovies()
                showUndoSnackBar()
            }
        }).attachToRecyclerView(rcyFavoriteMovie)
    }

    private fun showUndoSnackBar() {

        val snackBar = Snackbar.make(
            requireView(), getString(R.string.deleteFavoriteMovie), Snackbar.LENGTH_LONG
        )
        snackBar.setAction(getString(R.string.undo)) { undoDelete() }
            .setActionTextColor(resources.getColor(R.color.colorPrimary))
        snackBar.setActionTextColor(resources.getColor(R.color.colorAccent))
        snackBar.show()
    }

    private fun undoDelete() {
        favoriteMovieViewModel.insertFavoriteMovie(lastFavoriteMovieEntityDeleted)
        callGetListFavoriteMovies()
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rcyFavoriteMovie.layoutManager = layoutManager
        adapter = FavoriteMoviesAdapter(getFavoriteMovieId)
        rcyFavoriteMovie.adapter = adapter
    }

    private fun callGetListFavoriteMovies() {
        favoriteMovieViewModel.fetchAllFavoriteMovies()
        favoriteMovieViewModel.getAllFavoriteMovies().observe(this as LifecycleOwner, {
            it?.let { adapter.submitList(it) }
            pbrFavoriteMovie.visibility = View.GONE
        })
    }
}
