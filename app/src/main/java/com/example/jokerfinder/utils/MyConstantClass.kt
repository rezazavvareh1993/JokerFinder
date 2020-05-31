package com.example.jokerfinder.utils

import android.content.Context
import android.widget.Toast

class MyConstantClass {

    companion object {
        fun showToast(context : Context, message : String?){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        const val SPLASH_ACTIVITY_TIME: Long = 3000
        const val APY_KEY = "1ee72f0e064595f1d5adcd6a78cfb5c4"
        const val GITHUB_URL = "https://github.com/rezazavvareh1993/JokerFinder"
        const val LINKEDIN_URL = "https://linkedin.com/in/reza-zavvareh-0b738013a"


    }
}