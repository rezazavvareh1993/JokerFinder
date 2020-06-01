package com.example.jokerfinder.features.startpage

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.jokerfinder.R
import com.example.jokerfinder.features.MainActivity
import com.example.jokerfinder.utils.MyConstantClass
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        retriveTokenFcm()
        readFormatType()
        startActivityByDelay()
    }

    private fun retriveTokenFcm() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("MyTag", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                Log.d("MyTag", token)
            })
    }

    private fun readFormatType() {
        val typeFace = Typeface.createFromAsset(assets, "billion_stars_personal_use.ttf")
        txt_movie_finder.text = this.resources.getString(R.string.movie_finder)
        txt_movie_finder.typeface = typeFace
    }

    private fun startActivityByDelay() {

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, MyConstantClass.SPLASH_ACTIVITY_TIME)
    }
}

