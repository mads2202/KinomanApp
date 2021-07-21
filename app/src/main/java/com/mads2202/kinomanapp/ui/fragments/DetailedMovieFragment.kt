package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.DetailedMoviePageFragmentBinding
import com.mads2202.kinomanapp.ui.viewModels.DetailedMovieViewModel
import com.mads2202.kinomanapp.util.ID
import com.mads2202.kinomanapp.util.networkUtil.Status
import org.koin.android.viewmodel.ext.android.viewModel

class DetailedMovieFragment() : DetailedMovieFragmentParent() {
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
            navigateToDetailedPersonFragment(actor1.actorId,R.id.action_detailedMovieFragment_to_detailedActorFragment2)
        }
        binding.actor2.setOnClickListener {
            navigateToDetailedPersonFragment(actor2.actorId,R.id.action_detailedMovieFragment_to_detailedActorFragment2)
        }
        binding.actor3.setOnClickListener {
            navigateToDetailedPersonFragment(actor3.actorId,R.id.action_detailedMovieFragment_to_detailedActorFragment2)
        }
        binding.actor4.setOnClickListener {
            navigateToDetailedPersonFragment(actor4.actorId,R.id.action_detailedMovieFragment_to_detailedActorFragment2)
        }
        binding.director.setOnClickListener {
            navigateToDetailedPersonFragment(director.directorId,R.id.action_detailedMovieFragment_to_detailedActorFragment2)
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





}