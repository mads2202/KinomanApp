package com.mads2202.kinomanapp.ui.fragments

import android.graphics.drawable.Drawable
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mads2202.kinomanapp.databinding.DetailedMoviePageFragmentBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.DetailedMovie
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieParticipantRequest
import com.mads2202.kinomanapp.model.roomModel.Actor
import com.mads2202.kinomanapp.model.roomModel.Director


open class DetailedMovieFragmentParent : Fragment() {

    lateinit var binding: DetailedMoviePageFragmentBinding
    lateinit var movie: DetailedMovie
    protected lateinit var actor1: Actor
    protected lateinit var actor2: Actor
    protected lateinit var actor3: Actor
    protected lateinit var actor4: Actor
    protected lateinit var director: Director


    open fun bindMovie(movie: DetailedMovie) {
        this.movie = movie
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

    open fun bindMovieParticipant(movieParticipants: MovieParticipantRequest) {
        movieParticipants.crew.forEach {
            if (it.job == "Director") {
                director = Director(it.id, it.name)


            }
        }
        if (!director.name.isNullOrBlank()) {
            binding.director.text = director.name

        } else {
            binding.director.visibility = View.GONE
            binding.directorLabel.visibility = View.GONE
        }


        when (movieParticipants.cast.size) {
            0 -> {
                binding.actors.visibility = View.GONE
                binding.actorsLabel.visibility = View.GONE
            }
            1 -> {
                binding.actor1.text = movieParticipants.cast[0].name
                actor1 = Actor(movieParticipants.cast[0].id, movieParticipants.cast[0].name)
            }


            2 -> {

                binding.actor1.text = movieParticipants.cast[0].name
                binding.actor2.text = movieParticipants.cast[1].name
                actor1 = Actor(movieParticipants.cast[0].id, movieParticipants.cast[0].name)
                actor2 = Actor(movieParticipants.cast[1].id, movieParticipants.cast[1].name)
            }
            3 -> {
                binding.actor1.text = movieParticipants.cast[0].name
                binding.actor2.text = movieParticipants.cast[1].name
                binding.actor3.text = movieParticipants.cast[2].name
                actor1 = Actor(movieParticipants.cast[0].id, movieParticipants.cast[0].name)
                actor2 = Actor(movieParticipants.cast[1].id, movieParticipants.cast[1].name)
                actor3 = Actor(movieParticipants.cast[2].id, movieParticipants.cast[2].name)
            }
            else -> {
                binding.actor1.text = movieParticipants.cast[0].name
                binding.actor2.text = movieParticipants.cast[1].name
                binding.actor3.text = movieParticipants.cast[2].name
                binding.actor4.text = movieParticipants.cast[3].name
                actor1 = Actor(movieParticipants.cast[0].id, movieParticipants.cast[0].name)
                actor2 = Actor(movieParticipants.cast[1].id, movieParticipants.cast[1].name)
                actor3 = Actor(movieParticipants.cast[2].id, movieParticipants.cast[2].name)
                actor4 = Actor(movieParticipants.cast[3].id, movieParticipants.cast[3].name)
            }
        }
    }
}