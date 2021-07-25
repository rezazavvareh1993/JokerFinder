package com.example.jokerfinder.features.searchmovie

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jokerfinder.base.db.MovieDB
import com.example.jokerfinder.repository.DataRepository
import com.example.jokerfinder.repository.localrepository.FavoriteMovieDAO
import com.example.jokerfinder.repository.networkreopsitory.NetworkRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchMovieViewModelTest : TestCase() {

    private lateinit var viewModel: SearchMovieViewModel
    private lateinit var dataRepository: DataRepository


    @Before
    public override fun setUp() {
        super.setUp()

        val context = ApplicationProvider.getApplicationContext<Context>()

        val db = Room.inMemoryDatabaseBuilder(context, MovieDB::class.java)
            .allowMainThreadQueries().build()

//        val jokerFinderApiService = Retrofit.Builder()
//            .baseUrl("https://api.themoviedb.org/3/")
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build().create(JokerFinderApiService::class.java)

//        favoriteMovieDAO = db.favoriteMovieDAO()

//        networkRepository= NetworkRepository(jokerFinderApiService)

        dataRepository = DataRepository(db.favoriteMovieDAO())

        viewModel = SearchMovieViewModel(dataRepository)
    }

//    @Test
//    fun testGetMoviesPerName() {
//        viewModel.setMovieName("xx")
//        viewModel.fetchMovieSearchData()
//        viewModel.dataFlow.getOr
//
//    }

    @Test
    fun testSearchMovieName() {
        viewModel.setMovieName("x")
        val movieName = viewModel.getMovieName()
        assertEquals(movieName, "x")
    }
}