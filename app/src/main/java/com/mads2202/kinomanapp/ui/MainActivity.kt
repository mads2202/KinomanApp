package com.mads2202.kinomanapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.ui.fragments.MoviesListFragment
import com.mads2202.kinomanapp.ui.fragments.StartPageFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, StartPageFragment()).commit()
    }
}