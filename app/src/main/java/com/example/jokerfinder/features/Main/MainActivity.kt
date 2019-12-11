package com.example.jokerfinder.features.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jokerfinder.R
import com.example.jokerfinder.base.di.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
