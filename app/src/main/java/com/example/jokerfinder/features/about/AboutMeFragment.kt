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
import com.example.jokerfinder.databinding.FragmentAboutUsBinding
import com.example.jokerfinder.utils.MyConstantClass

/**
 * A simple [Fragment] subclass.
 */
class AboutMeFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.imgGithub.setOnClickListener(this)
        binding.imgLinkedin.setOnClickListener(this)
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
