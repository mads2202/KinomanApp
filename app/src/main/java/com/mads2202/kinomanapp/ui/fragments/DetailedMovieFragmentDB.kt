package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.DetailedMoviePageFragmentBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.DetailedMovie
import com.mads2202.kinomanapp.model.roomModel.MovieDB
import com.mads2202.kinomanapp.ui.viewModels.DetailedFavoriteMovieViewModel
import com.mads2202.kinomanapp.common.ID
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
        binding?.let { binding ->
            binding.detailedMoviePageProgressCircular.visibility = View.GONE
            binding.actor1.setOnClickListener {
                navigateToDetailedPersonFragment(
                    actor1.actorId,
                    R.id.action_detailedMovieFragmentDB_to_detailedActorFragment2
                )
            }
            binding.actor2.setOnClickListener {
                navigateToDetailedPersonFragment(
                    actor2.actorId,
                    R.id.action_detailedMovieFragmentDB_to_detailedActorFragment2
                )
            }
            binding.actor3.setOnClickListener {
                navigateToDetailedPersonFragment(
                    actor3.actorId,
                    R.id.action_detailedMovieFragmentDB_to_detailedActorFragment2
                )
            }
            binding.actor4.setOnClickListener {
                navigateToDetailedPersonFragment(
                    actor4.actorId,
                    R.id.action_detailedMovieFragmentDB_to_detailedActorFragment2
                )
            }
            binding.director.setOnClickListener {
                navigateToDetailedPersonFragment(
                    director.directorId,
                    R.id.action_detailedMovieFragmentDB_to_detailedActorFragment2
                )
            }
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
        viewModel.detailedMovie.observe(viewLifecycleOwner, {
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
        binding?.let { binding ->
            viewModel.actors.observe(viewLifecycleOwner, {
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

    }

    private fun setupDirectorObserver() {
        binding?.let { binding ->
            viewModel.director.observe(viewLifecycleOwner, {
                director = it
                binding.director.text = it.name
            })
        }
    }
}