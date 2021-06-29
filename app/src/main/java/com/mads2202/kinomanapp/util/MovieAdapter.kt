package com.mads2202.kinomanapp.util

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mads2202.kinomanapp.databinding.MovieListItemLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.Movie

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(DataDifferntiator) {
     var itemClickListener: OnItemClickListener?=null

    inner class MovieViewHolder(val binding: MovieListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                itemClickListener?.onItemClick(it,bindingAdapterPosition)
            }
        }
        fun bind(upcomingMovie: Movie) {
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/original/" + upcomingMovie.poster_path)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.moviePosterProgressCircular.visibility = View.VISIBLE

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.moviePosterProgressCircular.visibility = View.GONE
                        binding.poster.visibility = View.VISIBLE
                        return false
                    }

                })
                .thumbnail(0.3f)
                .into(binding.poster)
            binding.movieTitle.text = upcomingMovie.title
            binding.movieReleaseDate.text = upcomingMovie.release_date
            binding.movieRating.text = upcomingMovie.vote_average.toString()
        }


    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieAdapter.MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val movieListItemLayoutBinding =
            MovieListItemLayoutBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(movieListItemLayoutBinding)
    }

    object DataDifferntiator : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}