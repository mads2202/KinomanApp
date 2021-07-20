package com.mads2202.kinomanapp.util.adapters

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
import com.mads2202.kinomanapp.databinding.FavoriteMovieListItemLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.Movie
import com.mads2202.kinomanapp.model.roomModel.MovieDB

class FavoriteMovieAdapter(val movies: ArrayList<MovieDB>) :
    RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieVH>() {
    lateinit var itemClickListener: MovieAdapter.OnItemClickListener

    inner class FavoriteMovieVH(val binding: FavoriteMovieListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition)
            }
        }

        fun bind(movie: MovieDB) {
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/original/" + movie.posterPath)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressCircular.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressCircular.visibility = View.GONE
                        binding.poster.visibility = View.VISIBLE
                        return false
                    }

                })
                .thumbnail(0.3f)
                .into(binding.poster)
            binding.title.text = movie.title
            binding.releaseDate.text = movie.releaseDate
            binding.rating.text = movie.voteAverage.toString()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val movieListItemLayoutBinding =
            FavoriteMovieListItemLayoutBinding.inflate(layoutInflater, parent, false)
        return FavoriteMovieVH(movieListItemLayoutBinding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieVH, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}