package com.example.jokerfinder.features.splash

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.jokerfinder.R
import com.example.jokerfinder.databinding.ActivitySplashBinding
import com.example.jokerfinder.features.MainActivity
import com.example.jokerfinder.utils.MyConstantClass

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retriveTokenFcm()
        readFormatType()
        startActivityByDelay()
    }

    private fun retriveTokenFcm() {
//        FirebaseInstanceId.getInstance().instanceId
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.w("MyTag", "getInstanceId failed", task.exception)
//                    return@OnCompleteListener
//                }
//
//                // Get new Instance ID token
//                val token = task.result?.token
//                Log.d("MyTag", token!!)
//            })
    }

    private fun readFormatType() {
        val typeFace = Typeface.createFromAsset(assets, "billion_stars_personal_use.ttf")
        binding.txtMovieFinder.text = this.resources.getString(R.string.movie_finder)
        binding.txtMovieFinder.typeface = typeFace
    }

    private fun startActivityByDelay() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            },
            MyConstantClass.SPLASH_ACTIVITY_TIME
        )
    }
}
