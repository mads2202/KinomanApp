package com.mads2202.kinomanapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.ui.fragments.MoviesListFragment
import com.mads2202.kinomanapp.ui.fragments.StartPageFragment

class MainActivity : AppCompatActivity() {
    companion object{
        const val START_PAGE_FRAGMENT_TAG="StartPageFragmentTag"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
       val bottomNavigationBar=findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationBar.setupWithNavController(navController)

    }
}