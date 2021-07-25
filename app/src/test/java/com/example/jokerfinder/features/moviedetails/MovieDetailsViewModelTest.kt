package com.example.jokerfinder.features.moviedetails

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jokerfinder.base.db.MovieDB
import com.example.jokerfinder.getOrAwaitValue
import com.example.jokerfinder.repository.DataRepository
import junit.framework.TestCase
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailsViewModelTest: TestCase() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var dataRepository: DataRepository

    @Before
    override fun setUp() {
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

        viewModel = MovieDetailsViewModel(dataRepository)
    }

    @Test
    fun testFe3tDetailOfMovie() {
        viewModel.fetchMovieDetails(10)
        // Then the new task event is triggered
        val response = viewModel.getMovieDetailsData().getOrAwaitValue()
        assertThat(response.adult, CoreMatchers.not(CoreMatchers.nullValue()))
    }
}