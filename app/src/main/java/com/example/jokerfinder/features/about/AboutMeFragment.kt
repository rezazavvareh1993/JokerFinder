package com.example.jokerfinder.features.about


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.utils.MyConstantClass
import kotlinx.android.synthetic.main.fragment_about_us.*

/**
 * A simple [Fragment] subclass.
 */
class AboutMeFragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imgGithub.setOnClickListener(this)
        imgLinkedin.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imgGithub ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(MyConstantClass.GITHUB_URL)))
            R.id.imgLinkedin ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(MyConstantClass.LINKEDIN_URL)))
        }
    }
}
