package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.model.jsonModel.upcomingMovies.UpcomingMovie
import com.mads2202.kinomanapp.ui.viewModels.MovieViewModel
import com.mads2202.kinomanapp.util.MovieAdapter
import com.mads2202.kinomanapp.util.Status
import org.koin.android.viewmodel.ext.android.viewModel


class MoviesListFragment : Fragment() {
    lateinit var movieAdapter: MovieAdapter
    lateinit var mView: View
    private val movieViewModel: MovieViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.movie_list_fragment, container, false)
        setupUI()
        setupObserver()

        return mView
    }

    private fun setupObserver() {
        movieViewModel.results.observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val results = arrayListOf<UpcomingMovie>()
                    it.data?.let { result ->
                        results.addAll(result.results)
                    }
                    renderResults(results)
                }
            }
        })
    }


    private fun renderResults(upcomingMovies: List<UpcomingMovie>) {
        movieAdapter.addData(upcomingMovies)
        movieAdapter.notifyDataSetChanged()
    }

    private fun setupUI() {
        val recyclerView: RecyclerView = mView.findViewById(R.id.movie_list_fragment_recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        movieAdapter = MovieAdapter(arrayListOf())
        recyclerView.adapter = movieAdapter
    }

}