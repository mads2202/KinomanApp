package com.mads2202.kinomanapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.ui.fragments.MoviesListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.main_activity_fragment_container, MoviesListFragment()).commit()
    }
}