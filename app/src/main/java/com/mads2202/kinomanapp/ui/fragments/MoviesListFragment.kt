package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.MovieListFragmentBinding
import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.Movie
import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.MovieType
import com.mads2202.kinomanapp.ui.viewModels.MovieViewModel
import com.mads2202.kinomanapp.util.MovieAdapter
import com.mads2202.kinomanapp.util.NetworkHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class MoviesListFragment : Fragment() {
    companion object {
        const val ADAPTER_POSITION = "AdapterPosition"
        const val MOVIE_TYPE = "MovieType"
        var adapterPosition = 1
        fun newInstance(movieType: MovieType): MoviesListFragment {
            val args = Bundle()
            args.putSerializable(MOVIE_TYPE, movieType)
            val fragment = MoviesListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var movieAdapter: MovieAdapter
    lateinit var binding: MovieListFragmentBinding
    lateinit var mView: View
    private val movieViewModel: MovieViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.movie_list_fragment, container, false)
        binding = MovieListFragmentBinding.bind(mView)
        setupUI()
        if (savedInstanceState != null) {
            adapterPosition = savedInstanceState.getInt(ADAPTER_POSITION)
        }
        if (NetworkHelper(requireContext()).isNetworkConnected()) {
            binding.movieListProgressCircular.visibility = View.GONE
            loadingData()
        } else {
            binding.movieListFragmentRecyclerView.visibility = View.GONE
            binding.movieListProgressCircular.visibility = View.VISIBLE
        }
        return mView
    }



    private fun setupUI() {
        val recyclerView: RecyclerView = binding.movieListFragmentRecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        movieAdapter = MovieAdapter()
        recyclerView.adapter = movieAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(requireActivity(), LinearLayoutManager.HORIZONTAL)
        )
        movieAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        movieAdapter.itemClickListener = object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val movie = movieAdapter.snapshot()[position] as Movie
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_activity_fragment_container,
                        DetailedMovieFragment.newInstance(movie.id)
                    ).addToBackStack("DETAILEDMOVIEFRAGMENT")
                    .commit()

            }
        }
    }


    private fun loadingData() {

        when (arguments?.get(MOVIE_TYPE)) {
            MovieType.UPCOMING ->
                lifecycleScope.launch {
                    movieViewModel.upcomingMovieList.collect { pagingData ->
                        movieAdapter.submitData(pagingData)
                    }
                }
            MovieType.POPULAR -> lifecycleScope.launch {
                movieViewModel.popularMovieList.collect { pagingData ->
                    movieAdapter.submitData(pagingData)
                }
            }
            MovieType.TOPRATED -> lifecycleScope.launch {
                movieViewModel.topRatedMovieList.collect { pagingData ->
                    movieAdapter.submitData(pagingData)
                }
            }
        }


    }

}