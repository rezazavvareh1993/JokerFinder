package com.example.jokerfinder.features.startpage


import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.jokerfinder.R
import com.example.jokerfinder.utils.MyConstantClass
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        readFormatType()
        startActivityByDelay()
    }

    private fun startActivityByDelay() {
        val typeFace = Typeface.createFromAsset(context?.assets, "billion_stars_personal_use.ttf")
        txt_movie_finder.text = this.resources.getString(R.string.movie_finder)
        txt_movie_finder.typeface = typeFace
    }

    private fun readFormatType() {
        Handler().postDelayed({
            navController.navigate(R.id.action_splashFragment_to_searchMovieFragment)
            onDestroy()
        }, MyConstantClass.SPLASH_ACTIVITY_TIME)    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
}
