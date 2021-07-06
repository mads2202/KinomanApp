package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.StartPageFragmentLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieType
import com.mads2202.kinomanapp.ui.viewModels.MovieViewModel
import com.mads2202.kinomanapp.util.MovieAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class StartPageFragment : Fragment() {
    lateinit var binding: StartPageFragmentLayoutBinding
    companion object{
        const val NO_INTERNET_CONNECTION="No internet connection"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.start_page_fragment_layout, container, false)
        binding = StartPageFragmentLayoutBinding.bind(view)

        childFragmentManager.beginTransaction()
            .replace(
                R.id.upcoming_movie_container,
                MoviesListFragment.newInstance(MovieType.UPCOMING)
            )
            .replace(
                R.id.popular_movie_container,
                MoviesListFragment.newInstance(MovieType.POPULAR)
            )
            .replace(
                R.id.top_rated_movie_container,
                MoviesListFragment.newInstance(MovieType.TOPRATED)
            )
            .commit()


        return view
    }
}