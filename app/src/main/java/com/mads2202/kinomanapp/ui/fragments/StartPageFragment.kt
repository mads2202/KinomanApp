package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.StartPageFragmentLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.Movie
import com.mads2202.kinomanapp.ui.viewModels.StartPageViewModel
import com.mads2202.kinomanapp.util.ID
import com.mads2202.kinomanapp.util.adapters.MovieAdapter
import com.mads2202.kinomanapp.util.networkUtil.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class StartPageFragment : Fragment() {

    private val startPageViewModel: StartPageViewModel by viewModel()
    private lateinit var binding: StartPageFragmentLayoutBinding
    private lateinit var upcomingMoviesAdapter: MovieAdapter
    private lateinit var popularMoviesAdapter: MovieAdapter
    private lateinit var topRatedMoviesAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.start_page_fragment_layout, container, false)
        binding = StartPageFragmentLayoutBinding.bind(view)
        if (NetworkHelper(requireActivity()).isNetworkConnected()) {
            setupUI()
            loadingData()
        } else {
            setupNoConnectionUI()
        }

        return view
    }

    private fun setupUI() {

        val upcomingMoviesRecycler = binding.upcomingMovieRecycler
        upcomingMoviesRecycler.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        upcomingMoviesAdapter = MovieAdapter()
        upcomingMoviesRecycler.adapter = upcomingMoviesAdapter
        upcomingMoviesRecycler.addItemDecoration(
            DividerItemDecoration(requireActivity(), LinearLayoutManager.HORIZONTAL)
        )

        val popularMoviesRecycler = binding.popularMovieRecycler
        popularMoviesRecycler.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        popularMoviesAdapter = MovieAdapter()
        popularMoviesRecycler.adapter = popularMoviesAdapter
        popularMoviesRecycler.addItemDecoration(
            DividerItemDecoration(requireActivity(), LinearLayoutManager.HORIZONTAL)
        )

        val topRatedMovieRecycler = binding.topRatedMovieRecycler
        topRatedMovieRecycler.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        topRatedMoviesAdapter = MovieAdapter()
        topRatedMovieRecycler.adapter = topRatedMoviesAdapter
        topRatedMovieRecycler.addItemDecoration(
            DividerItemDecoration(requireActivity(), LinearLayoutManager.HORIZONTAL)
        )
        binding.refreshButton.visibility = View.GONE
        binding.startPageGroup.visibility = View.VISIBLE
        setUpItemClickListeners()
    }

    private fun setupNoConnectionUI() {
        binding.refreshButton.visibility = View.VISIBLE
        binding.startPageGroup.visibility = View.GONE
        binding.refreshButton.setOnClickListener {
            requireActivity().recreate()
            startPageViewModel.loadMovies()
        }
    }

    private fun loadingData() {
        lifecycleScope.launch(Dispatchers.IO) {
            startPageViewModel.upcomingMoviesList.collect { pagingData ->
                upcomingMoviesAdapter.submitData(pagingData)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            startPageViewModel.popularMoviesList.collect { pagingData ->
                popularMoviesAdapter.submitData(pagingData)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            startPageViewModel.topRatedMoviesList.collect { pagingData ->
                topRatedMoviesAdapter.submitData(pagingData)
            }
        }
    }

    private fun setUpItemClickListeners() {
        upcomingMoviesAdapter.itemClickListener = object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val movie = upcomingMoviesAdapter.snapshot()[position] as Movie
                val bundle = Bundle()
                movie.id?.let { bundle.putInt(ID, it) }
                if (NetworkHelper(requireActivity()).isNetworkConnected()) {
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_startPageFragment_to_detailedMovieFragment, bundle)
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.no_internet_connection,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        popularMoviesAdapter.itemClickListener = object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val movie = popularMoviesAdapter.snapshot()[position] as Movie
                val bundle = Bundle()
                movie.id?.let { bundle.putInt(ID, it) }
                if (NetworkHelper(requireActivity()).isNetworkConnected()) {
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_startPageFragment_to_detailedMovieFragment, bundle)
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.no_internet_connection,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        topRatedMoviesAdapter.itemClickListener = object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val movie = topRatedMoviesAdapter.snapshot()[position] as Movie
                val bundle = Bundle()
                movie.id?.let { bundle.putInt(ID, it) }
                if (NetworkHelper(requireActivity()).isNetworkConnected()) {
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_startPageFragment_to_detailedMovieFragment, bundle)
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.no_internet_connection,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}