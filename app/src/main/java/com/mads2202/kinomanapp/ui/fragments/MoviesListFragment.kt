package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.ListFragmentBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.Movie
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieType
import com.mads2202.kinomanapp.ui.viewModels.StartPageViewModel
import com.mads2202.kinomanapp.util.adapters.MovieAdapter
import com.mads2202.kinomanapp.util.networkUtil.NetworkHelper
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
    lateinit var binding: ListFragmentBinding
    lateinit var mView: View
    private val startPageViewModel: StartPageViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.list_fragment, container, false)
        binding = ListFragmentBinding.bind(mView)
        setupUI()
        if (savedInstanceState != null) {
            adapterPosition = savedInstanceState.getInt(ADAPTER_POSITION)
        }
        if (NetworkHelper(requireContext()).isNetworkConnected()) {
            binding.movieListProgressCircular.visibility = View.GONE
            /*loadingData()*/
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.movieListProgressCircular.visibility = View.VISIBLE
        }
        return mView
    }


    private fun setupUI() {
        val recyclerView: RecyclerView = binding.recyclerView
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
                /*requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.my_nav_host_fragment,
                        DetailedMovieFragment.newInstance(movie.id)
                    ).addToBackStack("DETAILEDMOVIEFRAGMENT")
                    .commit()*/
                val bundle = Bundle()
                movie.id?.let { bundle.putInt("ID", it) }
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_startPageFragment_to_detailedMovieFragment, bundle)
            }
        }
    }


    /*private fun loadingData() {

        when (arguments?.get(MOVIE_TYPE)) {
            MovieType.UPCOMING ->
                lifecycleScope.launch(Dispatchers.IO) {
                    startPageViewModel.upcomingMovieList.collect { pagingData ->
                        movieAdapter.submitData(pagingData)
                    }
                }
            MovieType.POPULAR -> lifecycleScope.launch(Dispatchers.IO) {
                startPageViewModel.popularMovieList.collect { pagingData ->
                    movieAdapter.submitData(pagingData)
                }
            }
            MovieType.TOPRATED -> lifecycleScope.launch(Dispatchers.IO) {
                startPageViewModel.topRatedMovieList.collect { pagingData ->
                    movieAdapter.submitData(pagingData)
                }
            }
        }


    }*/

}