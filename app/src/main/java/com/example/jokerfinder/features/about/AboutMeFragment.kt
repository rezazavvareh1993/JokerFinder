package com.example.jokerfinder.features.about


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseFragment
import com.example.jokerfinder.utils.MyConstantClass
import kotlinx.android.synthetic.main.fragment_about_us.*

/**
 * A simple [Fragment] subclass.
 */
class AboutMeFragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        img_github.setOnClickListener(this)
        img_linkedin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_github -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(MyConstantClass.GITHUB_URL)))
            R.id.img_linkedin -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(MyConstantClass.LINKEDIN_URL)))
        }
    }


}
