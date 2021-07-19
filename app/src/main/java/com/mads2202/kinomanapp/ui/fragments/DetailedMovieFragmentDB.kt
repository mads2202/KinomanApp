package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.DetailedMoviePageFragmentBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.DetailedMovie
import com.mads2202.kinomanapp.model.roomModel.MovieDB
import com.mads2202.kinomanapp.room.repository.MovieRepositoryDB
import com.mads2202.kinomanapp.ui.viewModels.DetailedFavoriteMovieViewModel
import com.mads2202.kinomanapp.util.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

import org.koin.android.viewmodel.ext.android.viewModel


class DetailedMovieFragmentDB : DetailedMovieFragmentParent() {
    private val viewModel: DetailedFavoriteMovieViewModel by viewModel()

    private lateinit var movieDB: MovieDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.movieId = requireArguments().getInt(ID)
        val view = inflater.inflate(R.layout.detailed_movie_page_fragment, container, false)
        binding = DetailedMoviePageFragmentBinding.bind(view)
        setupObservers()
        setupUI()
        return view
    }

    private fun setupUI() {
        binding.detailedMoviePageProgressCircular.visibility = View.GONE
        binding.actor1.setOnClickListener {
            navigateToDetailedPersonFragment(actor1.actorId)
        }
        binding.actor2.setOnClickListener {
            navigateToDetailedPersonFragment(actor2.actorId)
        }
        binding.actor3.setOnClickListener {
            navigateToDetailedPersonFragment(actor3.actorId)
        }
        binding.actor4.setOnClickListener {
            navigateToDetailedPersonFragment(actor4.actorId)
        }
        binding.director.setOnClickListener {
            navigateToDetailedPersonFragment(director.directorId)
        }
        setupClickListener()
    }


    private fun setupObservers() {
        setupMovieObserver()
        setupDirectorObserver()
        setupActorsObserver()

    }

    private fun setupMovieObserver() {
        viewModel.loadMovie()
        viewModel.detailedMovie.observe(requireActivity(), {
            movieDB = it
            movie = DetailedMovie(
                it.budget,
                it.genres,
                it.movieId,
                it.overview,
                it.posterPath,
                it.releaseDate,
                0,
                0,
                it.status,
                it.tagline,
                it.title,
                it.voteAverage,
                0
            )
            bindMovie(movie)
            viewModel.loadDirector(it.movieDirectorId)
        })
    }

    private fun setupActorsObserver() {
        viewModel.loadActors()
        viewModel.actors.observe(requireActivity(), {
            actor1 = it.actors[0]
            binding.actor1.text = it.actors[0].name
            actor2 = it.actors[1]
            binding.actor2.text = it.actors[1].name
            actor3 = it.actors[2]
            binding.actor3.text = it.actors[2].name
            actor4 = it.actors[3]
            binding.actor4.text = it.actors[3].name
        })

    }

    private fun setupDirectorObserver() {
        viewModel.director.observe(requireActivity(), {
            director = it
            binding.director.text = it.name
        })
    }

    fun navigateToDetailedPersonFragment(id: Int) {
        val bundle = Bundle()
        bundle.putInt(ID, id)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_detailedMovieFragmentDB_to_detailedActorFragment2, bundle)
    }

}