package com.example.jokerfinder.features.pagingtest


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseApplication
import com.example.jokerfinder.base.BaseFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search_movie.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SearchMovieFragmentTest : BaseFragment(), View.OnClickListener {


    /////////////////Injects
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    /////////////////////navController
    private lateinit var navController: NavController

    //////////////////////ViewModel
    private lateinit var searchMovieViewModel: SearchMovieViewModelTest

    /////////////////////Views
    private lateinit var imgSearchMovie: ImageView

    ////////////////////Disposable
    private val disposable = CompositeDisposable()

    ////////////////////Lambda Function
    private val getIdMovieLambdaFunction: (Int) -> Unit = {
        val bundle = bundleOf("movieId" to it)
        navController.navigate(R.id.action_searchMovieFragment_to_movieDetailsFragment, bundle)
    }

    ///////////////////Variable
    private var checkSearchButton = true
    private lateinit var myContext: Context

    ///////////////////Adapter
    private var adapter =
        TestAdapter(
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

        injectFactory()
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

    private fun injectFactory() {
        (activity?.application as BaseApplication)
            .getApplicationComponent()
            .injectToSearchMovieFragment(this)
    }

    private fun handleImgSearch(view: View) {
        setOnClicks(view)
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        imgSearchMovie = view.findViewById(R.id.imgSearch)
        searchMovieViewModel =
            ViewModelProvider(this, factory).get(SearchMovieViewModelTest::class.java)
    }

    private fun setOnClicks(view: View) {
        view.findViewById<ImageView>(R.id.imgSearch).setOnClickListener(this) //click

        disposable.add( //////when edt search is empty
            RxTextView
                .textChanges(edtSearch)
                .filter { it.isEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (!checkSearchButton)
                        imgSearch.setImageResource(R.drawable.ic_search)
                }
        )
    }

    private fun setUpRecyclerView() {

        recyclerMovie.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(myContext, RecyclerView.HORIZONTAL, false)
        recyclerMovie.layoutManager = layoutManager
        recyclerMovie.adapter = adapter
    }

    private fun callGetListMovies() {

        searchMovieViewModel.fetchMovieSearchData(getMovieName())
        searchMovieViewModel.getSearchMovieData().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            pbrSearch.visibility = View.GONE
        })
    }

    private fun getMovieName(): String {
        return edtSearch.text.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
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
