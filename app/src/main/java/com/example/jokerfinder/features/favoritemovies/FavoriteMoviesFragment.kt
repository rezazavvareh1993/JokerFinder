package com.example.jokerfinder.features.favoritemovies


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.base.db.FavoriteMovieEntity
import com.example.jokerfinder.base.extensions.makeGone
import com.example.jokerfinder.base.extensions.makeVisible
import com.example.jokerfinder.databinding.FragmentFavoriteMovieBinding
import com.example.jokerfinder.features.favoritemovies.adapter.FavoriteMoviesAdapter
import com.example.jokerfinder.features.moviedetails.MovieDetailsViewModel
import com.example.jokerfinder.utils.response.GeneralResponse
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import kotlinx.android.synthetic.main.item_empty_list.view.*

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class FavoriteMoviesFragment : BaseFragment() {
    private var _binding: FragmentFavoriteMovieBinding? = null

    private val binding get() = _binding!!
    private val favoriteMovieViewModel: FavoriteMovieViewModel by viewModels()
    private lateinit var lastFavoriteMovieEntityDeleted: FavoriteMovieEntity
    private lateinit var adapter: FavoriteMoviesAdapter
    private val getFavoriteMovieId: (Int) -> Unit = {
        val bundle = bundleOf("movieId" to it)
        findNavController().navigate(
            R.id.action_favoriteMovieFragment_to_movieDetailsFragment,
            bundle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        callGetListFavoriteMovies()
        setUpRecyclerView()
        itemTouchHelper()
        observeFavoriteMovieDataBase()
    }

    private fun observeFavoriteMovieDataBase() {
        favoriteMovieViewModel.getAllFavoriteMovies().removeObservers(viewLifecycleOwner)
        favoriteMovieViewModel.getAllFavoriteMovies().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource) {
                    is GeneralResponse.Loading -> binding.pbrFavoriteMovie.makeVisible()
                    is GeneralResponse.Success -> setDataToRecyclerView(resource.data)
                    is GeneralResponse.Error -> {
                        binding.pbrFavoriteMovie.makeGone()
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun setDataToRecyclerView(data: List<FavoriteMovieEntity>?) {
        if (data != null) {
            binding.root.emptyBoxLayout.makeGone()
            adapter.submitList(data)
        } else
            binding.root.emptyBoxLayout.makeVisible()
        binding.pbrFavoriteMovie.makeGone()
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
        binding.rcyFavoriteMovie.layoutManager = layoutManager
        adapter = FavoriteMoviesAdapter(getFavoriteMovieId)
        binding.rcyFavoriteMovie.adapter = adapter
    }

    private fun callGetListFavoriteMovies() {
        favoriteMovieViewModel.fetchAllFavoriteMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
