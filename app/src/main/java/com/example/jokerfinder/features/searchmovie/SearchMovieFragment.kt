package com.example.jokerfinder.features.searchmovie


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.di.BaseFragment
import com.example.jokerfinder.features.di.BaseViewModelFactory
import com.example.jokerfinder.features.di.DaggerprovideRepository
import com.example.jokerfinder.features.searchmovie.movieadapter.MoviesAdapter
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.utils.MyConstantClass
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_movie_list.edt_movie_name_search
import kotlinx.android.synthetic.main.activity_movie_list.img_search_movie
import kotlinx.android.synthetic.main.activity_movie_list.movie_recycler_view
import kotlinx.android.synthetic.main.activity_movie_list.progressBar_in_movie_list
import kotlinx.android.synthetic.main.activity_movie_list.swipeContainer
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SearchMovieFragment : BaseFragment() ,View.OnClickListener{

    @Inject
    lateinit var repository : DataRepository

    lateinit var factory: ViewModelProvider.Factory

    private lateinit var navController: NavController
    //////////////////////ViewModel
    private lateinit var searchMovieViewModel: SearchMovieViewModel

    /////////////////////Views
    private lateinit var imgSearchMovie: ImageView

    ////////////////////Disposable
    private val disposable = CompositeDisposable()

    ////////////////////Lambda Function
    private val getIdMovieLambdaFunction: (Int) -> Unit = {
        val bundle = bundleOf("idMovie" to it)
        navController.navigate(R.id.action_searchMovieFragment_to_movieDetailsFragment, bundle)
    }
    ///////////////////Variable
    private var checkSearchButton = true

    ///////////////////Adapter
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


        progressBar_in_movie_list.visibility = View.GONE

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
        DaggerprovideRepository.create().getSearchMovieFragment(this)
        navController = Navigation.findNavController(view)
        imgSearchMovie = view.findViewById(R.id.img_search_movie)
        factory = BaseViewModelFactory(repository)
        searchMovieViewModel = ViewModelProvider(this, factory).get(SearchMovieViewModel::class.java)
    }

    private fun setOnClicks(view: View) {
        view.findViewById<ImageView>(R.id.img_search_movie).setOnClickListener(this) //click

        disposable.add( //////when edt search is empty
            RxTextView
                .textChanges(edt_movie_name_search)
                .filter { it.isEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(!checkSearchButton)
                        img_search_movie.setImageResource(R.drawable.ic_search)
                }
        )
    }

    private fun setUpRecyclerView() {

        movie_recycler_view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        movie_recycler_view.layoutManager = layoutManager
        movie_recycler_view.adapter = adapter

        movie_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem = layoutManager.findLastVisibleItemPosition()
                val total = layoutManager.itemCount
                if (total > 0)
                    if (total - 1 == lastItem)
                        searchMovieViewModel.fetchMovieSearchData(edt_movie_name_search.text.toString(),  true)
            }
        })
    }

    private fun callGetListMovies() {
        searchMovieViewModel.fetchMovieSearchData(getMovieName(),false)
        searchMovieViewModel.getSearchMovieData().observe(this, Observer {
            if(it != null ){
                adapter.submitList(it)
            }else{
                MyConstantClass.showToast(requireContext(), context?.resources?.getString(R.string.error_connection))
            }
            progressBar_in_movie_list.visibility = View.GONE
            swipeContainer.isRefreshing = false
        })
    }

    private fun getMovieName(): String {
        return  edt_movie_name_search.text.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_search_movie -> {
                if(checkSearchButton){
                    callGetListMovies()
                    progressBar_in_movie_list.visibility = View.VISIBLE
                    img_search_movie.setImageResource(R.drawable.ic_clear_)
                }else{
                    edt_movie_name_search.text.clear()
                    img_search_movie.setImageResource(R.drawable.ic_search)
                }
                checkSearchButton = !checkSearchButton
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

}
