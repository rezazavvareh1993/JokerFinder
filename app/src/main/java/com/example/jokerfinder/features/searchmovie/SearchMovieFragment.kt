package com.example.jokerfinder.features.searchmovie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.features.searchmovie.movieadapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_movie.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class SearchMovieFragment : BaseFragment(), View.OnClickListener {

    private val searchMovieViewModel: SearchMovieViewModel by viewModels()
    private var checkSearchButton = true
    private var job: Job? = null
    private lateinit var navController: NavController
    private lateinit var imgSearchMovie: ImageView
    private lateinit var adapter: MoviesAdapter
    private val getIdMovieLambdaFunction: (Int) -> Unit = {
        val bundle = bundleOf("movieId" to it)
        navController.navigate(R.id.action_searchMovieFragment_to_movieDetailsFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        setUpRecyclerView()
        setDataToRecyclerView()
        setOnClickListener()
        handleLoading()
    }


    private fun init() {
        navController = Navigation.findNavController(requireView())

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        swipeContainer.setOnRefreshListener { setDataToRecyclerView() }

        edtSearch.addTextChangedListener {
            if (it.toString().isEmpty() && !checkSearchButton)
                imgSearch.setImageResource(R.drawable.ic_search)
        }
    }

    private fun setOnClickListener() {
        imgSearch.setOnClickListener(this)

    }

    private fun setUpRecyclerView() {
        recyclerMovie.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerMovie.layoutManager = layoutManager
        adapter = MoviesAdapter(getIdMovieLambdaFunction)
        recyclerMovie.adapter = adapter
    }

    private fun setDataToRecyclerView() {
        job?.cancel()
        if (edtSearch.text.toString().isNotEmpty()) {
            searchMovieViewModel.fetchMovieSearchData(edtSearch.text.toString())
            job = lifecycleScope.launch {
                searchMovieViewModel.dataFlow.collectLatest { adapter.submitData(it) }
            }
        } else
            cancelLoading()
    }

    private fun cancelLoading() {
        if (swipeContainer.isRefreshing)
            swipeContainer.isRefreshing = false
        pbrSearch.visibility = View.GONE
    }

    private fun handleLoading() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> pbrSearch.visibility = View.VISIBLE
                    is LoadState.Error, is LoadState.NotLoading -> {
                        cancelLoading()
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgSearch -> {
                if (checkSearchButton) {
                    pbrSearch.visibility = View.VISIBLE
                    setDataToRecyclerView()
                    imgSearch.setImageResource(R.drawable.ic_clear_)
                } else {
                    edtSearch.text.clear()
                    cancelLoading()
                    imgSearch.setImageResource(R.drawable.ic_search)
                }
                checkSearchButton = !checkSearchButton
            }
        }
    }
}
