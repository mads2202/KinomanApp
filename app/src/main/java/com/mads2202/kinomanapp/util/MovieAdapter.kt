package com.mads2202.kinomanapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mads2202.kinomanapp.databinding.MovieListItemLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.Result

class MovieAdapter(val results: ArrayList<Result>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    inner class MovieViewHolder(val binding: MovieListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            Glide.with(binding.root)
                .load(result.poster_path)
                .thumbnail(0.3f)
                .into(binding.poster)
            binding.movieTitle.text = result.title
            binding.movieReleaseDate.text = result.release_date
            binding.movieRating
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val movieListItemLayoutBinding =
            MovieListItemLayoutBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(movieListItemLayoutBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun addData(list: List<Result>) {
        results.addAll(list)
    }
}