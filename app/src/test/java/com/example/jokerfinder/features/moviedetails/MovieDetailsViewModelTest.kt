package com.example.jokerfinder.features.moviedetails

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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

        dataRepository = DataRepository()

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