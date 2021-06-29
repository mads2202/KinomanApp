package com.mads2202.kinomanapp.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.DetailedMoviePageFragmentBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.DetailedMovie
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieParticipantRequest
import com.mads2202.kinomanapp.retrofit.movieApi.ApiService
import com.mads2202.kinomanapp.ui.viewModels.DetailedMovieViewModel
import com.mads2202.kinomanapp.util.Status
import org.koin.android.ext.android.inject

class DetailedMovieFragment() : Fragment() {
    val apiService: ApiService by inject()
    lateinit var viewModel: DetailedMovieViewModel
    var movieId: Int = 0

    companion object {
        private const val MOVIE_ID = "MovieId"
        fun newInstance(id: Int?): DetailedMovieFragment {
            val args = Bundle()
            id?.let { args.putInt(MOVIE_ID, it) }
            val fragment = DetailedMovieFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var binding: DetailedMoviePageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieId = requireArguments().getInt(MOVIE_ID)
        val view = inflater.inflate(R.layout.detailed_movie_page_fragment, container, false)
        binding = DetailedMoviePageFragmentBinding.bind(view)
        viewModel = DetailedMovieViewModel(apiService, movieId)
        setupObservers()

        return view
    }

    private fun setupObservers() {
        setupMovieObserver()
        setupMovieParticipantObserver()
    }


    private fun setupMovieObserver() {
        viewModel.detailedMovieLiveData.observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.detailedMoviePageProgressCircular.visibility = View.GONE
                    it.data?.let { movie ->
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
        viewModel.movieParticipant.observe(requireActivity(), Observer {
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

    fun bindMovie(movie: DetailedMovie) {
        binding.title.text = movie.title
        binding.budget.text = movie.budget.toString()
        when (movie.genres.size) {
            0 -> {
                binding.genresLabel.visibility = View.GONE
                binding.genres.visibility = View.GONE
            }
            1 -> binding.genre1.text = movie.genres[0].name
            2 -> {
                binding.genre1.text = movie.genres[0].name
                binding.genre2.text = movie.genres[1].name
            }
            else -> {
                binding.genre1.text = movie.genres[0].name
                binding.genre2.text = movie.genres[1].name
                binding.genre3.text = movie.genres[2].name
            }

        }

        binding.shortDescriptions.text = movie.overview
        Glide.with(binding.root)
            .load("https://image.tmdb.org/t/p/original/" + movie.posterPath)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.detailedMoviePosterProgressCircular.visibility = View.VISIBLE

                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.detailedMoviePosterProgressCircular.visibility = View.GONE
                    binding.poster.visibility = View.VISIBLE
                    return false
                }

            })
            .thumbnail(0.3f)
            .into(binding.poster)

    }

    fun bindMovieParticipant(movieParticipants: MovieParticipantRequest) {
        var movieDirector: String? = ""

        movieParticipants.crew.forEach {
            if (it.job == "Director" && movieDirector.isNullOrBlank()) {
                movieDirector = it.name
            }
        }
        if (!movieDirector.isNullOrBlank()) {
            binding.director.text = movieDirector
        } else {
            binding.director.visibility = View.GONE
            binding.directorLabel.visibility = View.GONE
        }


        when (movieParticipants.cast.size) {
            0 -> {
                binding.actors.visibility = View.GONE
                binding.actorsLabel.visibility = View.GONE
            }
            1 -> binding.actor1.text = movieParticipants.cast[0].name
            2 -> {

                binding.actor1.text = movieParticipants.cast[0].name
                binding.actor2.text = movieParticipants.cast[1].name
            }
            3 -> {
                binding.actor1.text = movieParticipants.cast[0].name
                binding.actor2.text = movieParticipants.cast[1].name
                binding.actor3.text = movieParticipants.cast[2].name

            }
            else -> {
                binding.actor1.text = movieParticipants.cast[0].name
                binding.actor2.text = movieParticipants.cast[1].name
                binding.actor3.text = movieParticipants.cast[2].name
                binding.actor4.text = movieParticipants.cast[3].name
            }
        }
    }

}