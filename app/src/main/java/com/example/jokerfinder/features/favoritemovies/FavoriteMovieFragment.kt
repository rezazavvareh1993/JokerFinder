package com.example.jokerfinder.features.favoritemovies


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.features.di.BaseViewModelFactory
import com.example.jokerfinder.features.favoritemovies.favoritemoviesadapter.FavoriteMoviesAdapter
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.utils.MyConstantClass
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment : BaseFragment() {

    @Inject
    lateinit var repository : DataRepository

    lateinit var adapter : FavoriteMoviesAdapter

    lateinit var favoriteMovieViewModel: FavoriteMovieViewModel
    lateinit var factory : ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        callGetListFavoriteMovies()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val layoutManager =  LinearLayoutManager(context , RecyclerView.VERTICAL, false)
        favorite_movie_recycler_view.layoutManager = layoutManager
        favorite_movie_recycler_view.adapter = adapter
    }

    private fun callGetListFavoriteMovies() {
        favoriteMovieViewModel.getAllFavoriteMovies().observe(this, Observer {
            if(it != null)
                adapter.submitList(it)
            else
                MyConstantClass.showToast(requireContext(), context?.resources?.getString(R.string.error_connection))


        })
    }

    private fun init() {

        factory = BaseViewModelFactory(repository)
        favoriteMovieViewModel = ViewModelProvider(this, factory).get(FavoriteMovieViewModel::class.java)
    }
}
