package com.mads2202.kinomanapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mads2202.kinomanapp.App
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.FavoriteMoviesFragmentLayoutBinding
import com.mads2202.kinomanapp.model.roomModel.MovieDB
import com.mads2202.kinomanapp.ui.viewModels.FavoriteMovieViewModel
import com.mads2202.kinomanapp.common.ID
import com.mads2202.kinomanapp.ui.adapters.FavoriteMovieAdapter
import com.mads2202.kinomanapp.ui.adapters.MovieAdapter
import javax.inject.Inject

class FavoriteMoviesListFragment : Fragment() {
    @Inject
    lateinit var factory: FavoriteMovieViewModel.FavoriteMovieViewModelFactory
    private val favoriteMovieViewModel: FavoriteMovieViewModel by viewModels { factory }
    private var binding: FavoriteMoviesFragmentLayoutBinding? = null
    private val adapter: FavoriteMovieAdapter = FavoriteMovieAdapter(arrayListOf())
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.favorite_movies_fragment_layout, container, false)
        binding = FavoriteMoviesFragmentLayoutBinding.bind(view)
        setupObserver()
        setupUI()
        return view
    }

    private fun setupUI() {
        binding?.let { binding ->
            val recyclerView: RecyclerView = binding.favoriteMoviesRecycler
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)
            )
            adapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            adapter.itemClickListener = object : MovieAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val movie = adapter.movies[position]
                    val bundle = Bundle()
                    bundle.putInt(ID, movie.movieId)

                    Navigation.findNavController(binding.root)
                        .navigate(
                            R.id.action_favoriteMoviesListFragment_to_detailedMovieFragmentDB,
                            bundle
                        )
                }
            }
        }
    }

    private fun setupObserver() {
        favoriteMovieViewModel.favoriteMovies.observe(viewLifecycleOwner, {
            refreshAdapter(it)
        })
    }

    private fun refreshAdapter(movies: List<MovieDB>) {
        movies.forEach {
            if (!adapter.movies.contains(it)) {
                adapter.movies.add(it)
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
