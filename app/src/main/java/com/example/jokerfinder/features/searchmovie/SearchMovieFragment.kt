package com.example.jokerfinder.features.searchmovie


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.features.searchmovie.movieadapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_movie.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class SearchMovieFragment : BaseFragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private val searchMovieViewModel: SearchMovieViewModel by viewModels()
    private lateinit var imgSearchMovie: ImageView
    private var job: Job? = null
    private val getIdMovieLambdaFunction: (Int) -> Unit = {
        val bundle = bundleOf("movieId" to it)
        navController.navigate(R.id.action_searchMovieFragment_to_movieDetailsFragment, bundle)
    }
    private var checkSearchButton = true
    private lateinit var myContext: Context
    private var adapter =
        MoviesAdapter(
            getIdMovieLambdaFunction
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        pbrSearch.visibility = View.GONE

        init(view)
        callGetListMovies()
        setUpRecyclerView()
        handleImgSearch(view)


        swipeContainer.setOnRefreshListener {
            callGetListMovies()
        }
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

    }

    private fun handleImgSearch(view: View) {
        setOnClicks(view)
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        imgSearchMovie = view.findViewById(R.id.imgSearch)
    }

    private fun setOnClicks(view: View) {
        view.findViewById<ImageView>(R.id.imgSearch).setOnClickListener(this) //click
        edtSearch.addTextChangedListener {
            if (it.toString().isEmpty())
                if (!checkSearchButton)
                    imgSearch.setImageResource(R.drawable.ic_search)
        }
    }

    private fun setUpRecyclerView() {

        recyclerMovie.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(myContext, RecyclerView.HORIZONTAL, false)
        recyclerMovie.layoutManager = layoutManager
        recyclerMovie.adapter = adapter
//        recyclerMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val lastItem = layoutManager.findLastVisibleItemPosition()
//                val total = layoutManager.itemCount
//                if (total > 0)
//                    if (total - 1 == lastItem) {
//                        searchMovieViewModel.fetchMovieSearchData(edtSearch.text.toString(), true)
//                        pbrPagination.visibility = View.VISIBLE
//                    }
//            }
//        })
    }

    private fun callGetListMovies() {
        job?.cancel()
        if (getMovieName().isNotEmpty())
            job = lifecycleScope.launch {
                searchMovieViewModel.fetchMovieSearchData(getMovieName())?.collectLatest {
                    pbrSearch.visibility = View.GONE
                    pbrPagination.visibility = View.GONE
                    swipeContainer.isRefreshing = false
                    adapter.submitData(it)
                }
            }
    }

    private fun getMovieName(): String {
        return edtSearch.text.toString()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgSearch -> {
                if (checkSearchButton) {
                    callGetListMovies()
                    pbrSearch.visibility = View.VISIBLE
                    imgSearch.setImageResource(R.drawable.ic_clear_)
                } else {
                    edtSearch.text.clear()
                    imgSearch.setImageResource(R.drawable.ic_search)
                }
                checkSearchButton = !checkSearchButton
            }
        }
    }
}
