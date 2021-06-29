package com.mads2202.kinomanapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        if (supportFragmentManager.findFragmentByTag(START_PAGE_FRAGMENT_TAG) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, StartPageFragment(), START_PAGE_FRAGMENT_TAG)
                .addToBackStack(START_PAGE_FRAGMENT_TAG).commit()
        }
    }
}