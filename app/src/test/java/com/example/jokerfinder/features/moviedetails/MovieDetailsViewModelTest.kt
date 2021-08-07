package com.example.jokerfinder.features.moviedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jokerfinder.getOrAwaitValue
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun movieDetailsTest() {
        // Given a fresh ViewModel
        val movieDetailsViewModel =
            MovieDetailsViewModel(ApplicationProvider.getApplicationContext())

        // Then the new task event is triggered
        val value = movieDetailsViewModel.getMovieDetailsData().getOrAwaitValue()
        assertThat(value.adult, CoreMatchers.not(CoreMatchers.nullValue()))
    }
}
