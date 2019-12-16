package com.example.jokerfinder.features.favoritemovie


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jokerfinder.R
import com.example.jokerfinder.base.di.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }


}
