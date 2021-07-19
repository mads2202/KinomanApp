package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.android.material.snackbar.Snackbar
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.DetailedMoviePageFragmentBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.Genres
import com.mads2202.kinomanapp.model.roomModel.MovieActorCrossRef
import com.mads2202.kinomanapp.model.roomModel.MovieDB
import com.mads2202.kinomanapp.retrofit.movieApi.MovieRepository
import com.mads2202.kinomanapp.room.repository.MovieRepositoryDB
import com.mads2202.kinomanapp.ui.viewModels.DetailedMovieViewModel
import com.mads2202.kinomanapp.util.ID
import com.mads2202.kinomanapp.util.dbUtil.Converters
import com.mads2202.kinomanapp.util.networkUtil.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class DetailedMovieFragment() : DetailedMovieFragmentParent() {
    private val movieRepository: MovieRepository by inject()
    private val movieRepositoryDB: MovieRepositoryDB by inject()
    private val detailedMovieViewModel: DetailedMovieViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailedMovieViewModel.id = requireArguments().getInt(ID)
        detailedMovieViewModel.loadDetailedMovie()
        val view = inflater.inflate(R.layout.detailed_movie_page_fragment, container, false)
        binding = DetailedMoviePageFragmentBinding.bind(view)
        setupObservers()
        setupUI()
        return view
    }

    private fun setupUI() {

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
        setupMovieParticipantObserver()
    }


    private fun setupMovieObserver() {
        detailedMovieViewModel.detailedMovieLiveData.observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.detailedMoviePageProgressCircular.visibility = View.GONE
                    it.data?.let { it ->
                        movie = it
                        bindMovie(movie)
                    }

                }
                Status.LOADING -> {
                    binding.detailedMoviePageProgressCircular.visibility = View.VISIBLE
                    binding.detailedMoviePageElementsGroup.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.detailedMoviePageProgressCircular.visibility = View.GONE
                    it.message?.let { it1 ->
                        Snackbar.make(
                            binding.root,
                            it1, Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }

    private fun setupMovieParticipantObserver() {
        detailedMovieViewModel.movieParticipant.observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.detailedMoviePageProgressCircular.visibility = View.GONE
                    it.data?.let { movieParticipant ->
                        bindMovieParticipant(movieParticipant)
                    }

                }
            }
        })
    }

    fun navigateToDetailedPersonFragment(id: Int) {
        val bundle = Bundle()
        bundle.putInt(ID, id)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_detailedMovieFragment_to_detailedActorFragment2, bundle)
    }


}