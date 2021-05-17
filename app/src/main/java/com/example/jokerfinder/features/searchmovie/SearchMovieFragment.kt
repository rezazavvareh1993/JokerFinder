package com.example.jokerfinder.features.searchmovie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.base.extensions.makeInVisible
import com.example.jokerfinder.base.extensions.makeVisible
import com.example.jokerfinder.databinding.FragmentSearchMovieBinding
import com.example.jokerfinder.features.searchmovie.movieadapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass for search movies
 */
@AndroidEntryPoint
class SearchMovieFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentSearchMovieBinding? = null
    private val binding get() = _binding!!
    private val searchMovieViewModel: SearchMovieViewModel by viewModels()
    private var checkSearchButton = true
    private var job: Job? = null
    private lateinit var adapter: MoviesAdapter
    private val getIdMovieLambdaFunction: (Int) -> Unit = {
        val bundle = bundleOf("movieId" to it)
        findNavController().navigate(
            R.id.action_searchMovieFragment_to_movieDetailsFragment,
            bundle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        setUpRecyclerView()
        setDataToRecyclerView()
        handleLoading()
    }


    private fun init() {
        binding.imgSearch.setOnClickListener(this)
        // Configure the refreshing colors
        binding.swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        binding.swipeContainer.setOnRefreshListener { setDataToRecyclerView() }

        binding.edtSearch.addTextChangedListener {
            if (it.toString().isEmpty() && !checkSearchButton)
                binding.imgSearch.setImageResource(R.drawable.ic_search)
        }
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.recyclerMovie.layoutManager = layoutManager
        adapter = MoviesAdapter(getIdMovieLambdaFunction)
        binding.recyclerMovie.adapter = adapter
    }

    private fun setDataToRecyclerView() {
        job?.cancel()
        if (searchMovieViewModel.getMovieName().isNotEmpty()) {
            searchMovieViewModel.fetchMovieSearchData()
            job = lifecycleScope.launch {
                searchMovieViewModel.dataFlow.collectLatest { adapter.submitData(it) }
            }
        } else
            cancelLoading()
    }

    private fun cancelLoading() {
        if (binding.swipeContainer.isRefreshing)
            binding.swipeContainer.isRefreshing = false
        binding.pbrSearch.makeInVisible()
    }

    private fun handleLoading() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> binding.pbrSearch.makeVisible()
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
                    searchMovieViewModel.setMovieName(binding.edtSearch.text.toString())
                    if(!searchMovieViewModel.isSameName()) {
                        binding.pbrSearch.makeVisible()
                        setDataToRecyclerView()
                        binding.imgSearch.setImageResource(R.drawable.ic_clear)
                    }
                } else {
                    binding.edtSearch.text.clear()
                    cancelLoading()
                    binding.imgSearch.setImageResource(R.drawable.ic_search)
                }
                checkSearchButton = !checkSearchButton
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
