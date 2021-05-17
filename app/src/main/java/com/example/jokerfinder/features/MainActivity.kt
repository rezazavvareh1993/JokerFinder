package com.example.jokerfinder.features

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.jokerfinder.R
import com.example.jokerfinder.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var mFirebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
//
//        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

//        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setUpBottomNav(navController)

//        // [START retrieve_current_token]
//        FirebaseInstanceId.getInstance().instanceId
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.w("MyTag", "getInstanceId failed", task.exception)
//                    return@OnCompleteListener
//                }
//
//                // Get new Instance ID token
//                val token = task.result?.token
//
//                // Log and toast
////                val msg = getString(R.string.msg_token_fmt, token)
//                Log.d("MyTag", token!!)
//                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
//            })
//         [END retrieve_current_token]
    }

    private fun setUpBottomNav(navController: NavController) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
}





