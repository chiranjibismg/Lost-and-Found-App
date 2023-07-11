package com.bloodreaper.lostandfound

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MainScreen()
//        }
//    }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.lost_fragment,R.id.profile_fragment,R.id.found_fragment)
        )

        setSupportActionBar(toolbar_login)
        setupActionBarWithNavController(navController,appBarConfiguration)
        bottom_navigation_bar.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    override fun onBackPressed() {
        Log.i(TAG, "onBackPressed--")
        if (_doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this._doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { _doubleBackToExitPressedOnce = false }, 2000)
    }

}