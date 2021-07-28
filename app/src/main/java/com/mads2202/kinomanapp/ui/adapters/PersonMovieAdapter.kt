package com.mads2202.kinomanapp.ui.adapters

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
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.PersonMoviesListItemLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.personModel.PersonMovies

class PersonMovieAdapter(val movies: ArrayList<PersonMovies>) :
    RecyclerView.Adapter<PersonMovieAdapter.PersonMovieVH>() {
    lateinit var itemClickListener: MovieAdapter.OnItemClickListener

    inner class PersonMovieVH(val binding: PersonMoviesListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(it, bindingAdapterPosition)

            }
        }

        fun bind(personMovie: PersonMovies) {
            binding.movieTitle.text = personMovie.title
            binding.role.text = personMovie.job
            binding.releaseDate.text = personMovie.releaseDate
            binding.movieRating.text = personMovie.voteAverage.toString()
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/original/" + personMovie.posterPath)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.image_loading)
                .thumbnail(0.3f)
                .into(binding.poster)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonMovieVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_movies_list_item_layout, parent, false)
        val binding = PersonMoviesListItemLayoutBinding.bind(view)
        return PersonMovieVH(binding)
    }

    override fun onBindViewHolder(holder: PersonMovieVH, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}