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
import androidx.recyclerview.widget.RecyclerView
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.StartPageFragmentLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.Movie
import com.mads2202.kinomanapp.ui.viewModels.StartPageViewModel
import com.mads2202.kinomanapp.common.ID
import com.mads2202.kinomanapp.ui.adapters.MovieAdapter
import com.mads2202.kinomanapp.common.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class StartPageFragment : Fragment() {

    private val startPageViewModel: StartPageViewModel by viewModel()
    private var binding: StartPageFragmentLayoutBinding? = null
    private var upcomingMoviesAdapter: MovieAdapter = MovieAdapter()
    private var popularMoviesAdapter: MovieAdapter = MovieAdapter()
    private var topRatedMoviesAdapter: MovieAdapter = MovieAdapter()


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
        binding?.let { binding ->

            val upcomingMoviesRecycler = binding.upcomingMovieRecycler
            upcomingMoviesRecycler.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            upcomingMoviesRecycler.adapter = upcomingMoviesAdapter
            upcomingMoviesAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            upcomingMoviesRecycler.addItemDecoration(
                DividerItemDecoration(requireActivity(), LinearLayoutManager.HORIZONTAL)
            )

            val popularMoviesRecycler = binding.popularMovieRecycler
            popularMoviesRecycler.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            popularMoviesRecycler.adapter = popularMoviesAdapter
            popularMoviesAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            popularMoviesRecycler.addItemDecoration(
                DividerItemDecoration(requireActivity(), LinearLayoutManager.HORIZONTAL)
            )

            val topRatedMovieRecycler = binding.topRatedMovieRecycler
            topRatedMovieRecycler.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            topRatedMovieRecycler.adapter = topRatedMoviesAdapter
            topRatedMoviesAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            topRatedMovieRecycler.addItemDecoration(
                DividerItemDecoration(requireActivity(), LinearLayoutManager.HORIZONTAL)
            )
            binding.refreshButton.visibility = View.GONE
            binding.startPageGroup.visibility = View.VISIBLE
        }
        setUpItemClickListeners()
    }

    private fun setupNoConnectionUI() {
        binding?.let { binding ->
            binding.refreshButton.visibility = View.VISIBLE
            binding.startPageGroup.visibility = View.GONE
            binding.refreshButton.setOnClickListener {
                if (NetworkHelper(requireContext()).isNetworkConnected()) {
                    startPageViewModel.loadMovies()
                    setupUI()
                    binding.refreshButton.visibility = View.GONE
                    binding.startPageGroup.visibility = View.VISIBLE
                }
            }
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
        binding?.let { binding ->
            upcomingMoviesAdapter.itemClickListener = object : MovieAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val movie = upcomingMoviesAdapter.snapshot()[position] as Movie
                    val bundle = Bundle()
                    bundle.putInt(ID, movie.id)
                    if (NetworkHelper(requireActivity()).isNetworkConnected()) {
                        Navigation.findNavController(binding.root)
                            .navigate(
                                R.id.action_startPageFragment_to_detailedMovieFragment,
                                bundle
                            )
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
                    bundle.putInt(ID, movie.id)
                    if (NetworkHelper(requireActivity()).isNetworkConnected()) {
                        Navigation.findNavController(binding.root)
                            .navigate(
                                R.id.action_startPageFragment_to_detailedMovieFragment,
                                bundle
                            )
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
                    bundle.putInt(ID, movie.id)
                    if (NetworkHelper(requireActivity()).isNetworkConnected()) {
                        Navigation.findNavController(binding.root)
                            .navigate(
                                R.id.action_startPageFragment_to_detailedMovieFragment,
                                bundle
                            )
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}