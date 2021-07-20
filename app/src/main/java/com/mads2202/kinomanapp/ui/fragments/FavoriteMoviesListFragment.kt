package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.FavoriteMoviesFragmentLayoutBinding
import com.mads2202.kinomanapp.model.roomModel.MovieDB
import com.mads2202.kinomanapp.ui.viewModels.FavoriteMovieViewModel
import com.mads2202.kinomanapp.util.ID
import com.mads2202.kinomanapp.util.adapters.FavoriteMovieAdapter
import com.mads2202.kinomanapp.util.adapters.MovieAdapter
import org.koin.android.viewmodel.ext.android.viewModel


class FavoriteMoviesListFragment : Fragment() {
    private val favoriteMovieViewModel: FavoriteMovieViewModel by viewModel()
    private lateinit var binding: FavoriteMoviesFragmentLayoutBinding
    private val adapter: FavoriteMovieAdapter = FavoriteMovieAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.favorite_movies_fragment_layout, container, false)
        binding = FavoriteMoviesFragmentLayoutBinding.bind(view)
        setupObserver()
        setupUI()
        return view
    }

    private fun setupUI() {
        val recyclerView: RecyclerView = binding.favoriteMoviesRecycler
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)
        )
        adapter.itemClickListener = object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val movie = adapter.movies[position]
                val bundle = Bundle()
                movie.movieId?.let { bundle.putInt(ID, it) }

                Navigation.findNavController(binding.root)
                    .navigate(
                        R.id.action_favoriteMoviesListFragment_to_detailedMovieFragmentDB,
                        bundle
                    )
            }
        }
    }

    private fun setupObserver() {
        favoriteMovieViewModel.favoriteMovies.observe(requireActivity(), Observer {
            refreshAdapter(it)
        })
    }

    private fun refreshAdapter(movies: List<MovieDB>) {
        adapter.movies.addAll(movies)
        adapter.notifyDataSetChanged()
    }


}