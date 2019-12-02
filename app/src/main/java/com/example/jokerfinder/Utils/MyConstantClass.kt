package com.example.jokerfinder.Utils

import android.content.Context
import android.widget.Toast

class MyConstantClass {

    companion object {

        fun showToast(context : Context, message : String?){
            Toast.makeText(context, message, Toast.LENGTH_LONG)

        }

        const val SPLASH_ACTIVITY_TIME: Long = 3000
        const val APY_KEY = "1ee72f0e064595f1d5adcd6a78cfb5c4"
        const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZWU3MmYwZTA2NDU5NWYxZDVhZGNkNmE3OGNm" +
                "YjVjNCIsInN1YiI6IjVkZGU1MjBhODhiYmU2MDAxODE4ZDg5YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJ" +
                "dLCJ2ZXJzaW9uIjoxfQ.krZO2czE2_bCLgQdaIS1ja9vgdAiO0CPlQdaiUXCBuA"
    }
}