package com.mads2202.kinomanapp.util

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mads2202.kinomanapp.databinding.MovieListItemLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.UpcomingMovie

class MovieAdapter(val upcomingMovies: ArrayList<UpcomingMovie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    inner class MovieViewHolder(val binding: MovieListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(upcomingMovie: UpcomingMovie) {
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w185/" + upcomingMovie.poster_path)
                .listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.moviePosterProgressCircular.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.moviePosterProgressCircular.visibility=View.GONE
                        return false
                    }

                })
                .thumbnail(0.3f)
                .into(binding.poster)
            binding.movieTitle.text = upcomingMovie.title
            binding.movieReleaseDate.text = upcomingMovie.release_date
            binding.movieRating.text= upcomingMovie.vote_average.toString()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val movieListItemLayoutBinding =
            MovieListItemLayoutBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(movieListItemLayoutBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(upcomingMovies[position])
    }

    override fun getItemCount(): Int {
        return upcomingMovies.size
    }

    fun addData(list: List<UpcomingMovie>) {
        upcomingMovies.addAll(list)
    }
}