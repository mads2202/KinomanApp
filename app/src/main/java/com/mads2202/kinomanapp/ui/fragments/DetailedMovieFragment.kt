package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.DetailedMoviePageFragmentBinding
import com.mads2202.kinomanapp.ui.viewModels.DetailedMovieViewModel
import com.mads2202.kinomanapp.common.ID
import com.mads2202.kinomanapp.common.NetworkHelper
import com.mads2202.kinomanapp.common.Status
import org.koin.android.viewmodel.ext.android.viewModel

class DetailedMovieFragment : DetailedMovieFragmentParent() {
    private val detailedMovieViewModel: DetailedMovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailedMovieViewModel.id = requireArguments().getInt(ID)
        val view = inflater.inflate(R.layout.detailed_movie_page_fragment, container, false)
        binding = DetailedMoviePageFragmentBinding.bind(view)
        if (NetworkHelper(requireContext()).isNetworkConnected()) {
            detailedMovieViewModel.loadDetailedMovie()
            setupObservers()
            setupUI()
        } else {
            setupNoConnectionUI()
        }
        return view
    }

    private fun setupNoConnectionUI() {
        binding?.let { binding ->
            binding.refreshButton.visibility = View.VISIBLE
            binding.detailedMoviePageElementsGroup.visibility = View.GONE
            binding.refreshButton.setOnClickListener {
                if (NetworkHelper(requireContext()).isNetworkConnected()) {
                    setupObservers()
                    binding.refreshButton.visibility = View.GONE
                    binding.detailedMoviePageElementsGroup.visibility = View.VISIBLE
                    setupUI()
                }
            }
        }
    }

    private fun setupUI() {
        binding?.let { binding ->
            binding.actor1.setOnClickListener {
                navigateToDetailedPersonFragment(
                    actor1.actorId,
                    R.id.action_detailedMovieFragment_to_detailedActorFragment2
                )
            }
            binding.actor2.setOnClickListener {
                navigateToDetailedPersonFragment(
                    actor2.actorId,
                    R.id.action_detailedMovieFragment_to_detailedActorFragment2
                )
            }
            binding.actor3.setOnClickListener {
                navigateToDetailedPersonFragment(
                    actor3.actorId,
                    R.id.action_detailedMovieFragment_to_detailedActorFragment2
                )
            }
            binding.actor4.setOnClickListener {
                navigateToDetailedPersonFragment(
                    actor4.actorId,
                    R.id.action_detailedMovieFragment_to_detailedActorFragment2
                )
            }
            binding.director.setOnClickListener {
                navigateToDetailedPersonFragment(
                    director.directorId,
                    R.id.action_detailedMovieFragment_to_detailedActorFragment2
                )
            }
            binding.detailedMoviePageProgressCircular.visibility = View.GONE
        }
        setupClickListener()
    }


    private fun setupObservers() {
        setupMovieObserver()
        setupMovieParticipantObserver()
    }


    private fun setupMovieObserver() {
        binding?.let { binding ->
            detailedMovieViewModel.detailedMovieLiveData.observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.detailedMoviePageProgressCircular.visibility = View.GONE
                        it.data?.let { detailedMovie ->
                            movie = detailedMovie
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
    }

    private fun setupMovieParticipantObserver() {
        binding?.let { binding ->
            detailedMovieViewModel.movieParticipant.observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.detailedMoviePageProgressCircular.visibility = View.GONE
                        it.data?.let { movieParticipant ->
                            bindMovieParticipant(movieParticipant)
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                    }
                }
            })
        }
    }
}