package com.mads2202.kinomanapp.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.DetailedMoviePageFragmentBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.DetailedMovie
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.MovieParticipantRequest
import com.mads2202.kinomanapp.model.roomModel.Actor
import com.mads2202.kinomanapp.model.roomModel.Director
import com.mads2202.kinomanapp.model.roomModel.MovieActorCrossRef
import com.mads2202.kinomanapp.model.roomModel.MovieDB
import com.mads2202.kinomanapp.room.repository.MovieRepositoryDB
import com.mads2202.kinomanapp.common.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject


open class DetailedMovieFragmentParent : Fragment() {
    private val movieRepositoryDB: MovieRepositoryDB by inject()
    var binding: DetailedMoviePageFragmentBinding? = null
    lateinit var movie: DetailedMovie
    protected var actor1: Actor = Actor(1, "", 1)
    protected var actor2: Actor = Actor(1, "", 1)
    protected var actor3: Actor = Actor(1, "", 1)
    protected var actor4: Actor = Actor(1, "", 1)
    protected var director: Director = Director(1, "", 1)


    open fun bindMovie(movie: DetailedMovie) {
        binding?.let { binding ->
            this.movie = movie
            binding.detailedMoviePageProgressCircular.visibility = View.GONE
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
                .error(R.drawable.no_image)
                .placeholder(R.drawable.image_loading)
                .thumbnail(0.3f)
                .into(binding.poster)
        }
    }

    open fun bindMovieParticipant(movieParticipants: MovieParticipantRequest) {
        binding?.let { binding ->
            movieParticipants.crew.forEach {
                if (it.job == getString(R.string.director)) {
                    director = Director(it.id, it.name, requireArguments().getInt(ID))


                }
            }
            if (director.name.isNotBlank()) {
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
                    actor1 =
                        Actor(
                            movieParticipants.cast[0].id,
                            movieParticipants.cast[0].name,
                            requireArguments().getInt(ID)
                        )
                }


                2 -> {

                    binding.actor1.text = movieParticipants.cast[0].name
                    binding.actor2.text = movieParticipants.cast[1].name
                    actor1 =
                        Actor(
                            movieParticipants.cast[0].id,
                            movieParticipants.cast[0].name,
                            requireArguments().getInt(ID)
                        )
                    actor2 =
                        Actor(
                            movieParticipants.cast[1].id,
                            movieParticipants.cast[1].name,
                            requireArguments().getInt(ID)
                        )
                }
                3 -> {
                    binding.actor1.text = movieParticipants.cast[0].name
                    binding.actor2.text = movieParticipants.cast[1].name
                    binding.actor3.text = movieParticipants.cast[2].name
                    actor1 =
                        Actor(
                            movieParticipants.cast[0].id,
                            movieParticipants.cast[0].name,
                            requireArguments().getInt(ID)
                        )
                    actor2 =
                        Actor(
                            movieParticipants.cast[1].id,
                            movieParticipants.cast[1].name,
                            requireArguments().getInt(ID)
                        )
                    actor3 =
                        Actor(
                            movieParticipants.cast[2].id,
                            movieParticipants.cast[2].name,
                            requireArguments().getInt(ID)
                        )
                }
                else -> {
                    binding.actor1.text = movieParticipants.cast[0].name
                    binding.actor2.text = movieParticipants.cast[1].name
                    binding.actor3.text = movieParticipants.cast[2].name
                    binding.actor4.text = movieParticipants.cast[3].name
                    actor1 =
                        Actor(
                            movieParticipants.cast[0].id,
                            movieParticipants.cast[0].name,
                            requireArguments().getInt(ID)
                        )
                    actor2 =
                        Actor(
                            movieParticipants.cast[1].id,
                            movieParticipants.cast[1].name,
                            requireArguments().getInt(ID)
                        )
                    actor3 =
                        Actor(
                            movieParticipants.cast[2].id,
                            movieParticipants.cast[2].name,
                            requireArguments().getInt(ID)
                        )
                    actor4 =
                        Actor(
                            movieParticipants.cast[3].id,
                            movieParticipants.cast[3].name,
                            requireArguments().getInt(ID)
                        )
                }
            }
        }
    }

    protected fun setupClickListener() {
        binding?.let { binding ->
            lifecycleScope.launch(Dispatchers.IO) {
                val dbMovie = movieRepositoryDB.getMovieById(requireArguments().getInt(ID))
                withContext(Dispatchers.Main) {
                    if (dbMovie == null) {
                        binding.like.visibility = View.VISIBLE
                        binding.dislike.visibility = View.GONE
                    } else {
                        binding.like.visibility = View.GONE
                        binding.dislike.visibility = View.VISIBLE
                    }
                }
            }
            binding.dislike.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    movieRepositoryDB.deleteMovie(requireArguments().getInt(ID))
                    movieRepositoryDB.deleteActor(requireArguments().getInt(ID))
                    movieRepositoryDB.deleteDirector(requireArguments().getInt(ID))
                    movieRepositoryDB.deleteMovieActorCrossRef(requireArguments().getInt(ID))
                    withContext(Dispatchers.Main) {
                        binding.like.visibility = View.VISIBLE
                        binding.dislike.visibility = View.GONE
                    }
                }
            }
            binding.like.setOnClickListener {
                val movieDB = MovieDB(
                    movieId = requireArguments().getInt(ID),
                    movieDirectorId = director.directorId,
                    budget = movie.budget,
                    genres = movie.genres,
                    overview = movie.overview,
                    posterPath = movie.posterPath,
                    releaseDate = movie.releaseDate,
                    voteAverage = movie.voteAverage,
                    tagline = movie.tagline,
                    title = movie.title,
                    status = movie.status
                )
                binding.like.visibility = View.GONE
                binding.dislike.visibility = View.VISIBLE
                lifecycleScope.launch(Dispatchers.IO) {
                    movieRepositoryDB.addMovie(movieDB)
                    movieRepositoryDB.addDirector(director)
                    movieRepositoryDB.addActors(listOf(actor1, actor2, actor3, actor4))
                    movieRepositoryDB.addMovieActorCrossRef(
                        listOf(
                            MovieActorCrossRef(movieDB.movieId, actor1.actorId),
                            MovieActorCrossRef(movieDB.movieId, actor2.actorId),
                            MovieActorCrossRef(movieDB.movieId, actor3.actorId),
                            MovieActorCrossRef(movieDB.movieId, actor4.actorId)
                        )
                    )
                }
            }
        }
    }

    protected fun navigateToDetailedPersonFragment(id: Int, nav_action: Int) {
        val bundle = Bundle()
        bundle.putInt(ID, id)
        binding?.let { binding ->
            Navigation.findNavController(binding.root)
                .navigate(nav_action, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
