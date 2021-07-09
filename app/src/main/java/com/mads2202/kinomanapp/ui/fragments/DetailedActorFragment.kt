package com.mads2202.kinomanapp.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.DetailedActorFragmentLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.moviesModel.Movie
import com.mads2202.kinomanapp.model.jsonModel.personModel.Person
import com.mads2202.kinomanapp.model.jsonModel.personModel.PersonMovies
import com.mads2202.kinomanapp.ui.viewModels.PersonViewModel
import com.mads2202.kinomanapp.util.ID
import com.mads2202.kinomanapp.util.adapters.MovieAdapter
import com.mads2202.kinomanapp.util.adapters.PersonMovieAdapter
import com.mads2202.kinomanapp.util.networkUtil.NetworkHelper
import com.mads2202.kinomanapp.util.networkUtil.Status
import org.koin.android.viewmodel.ext.android.viewModel

class DetailedActorFragment : Fragment() {
    private val personViewModel: PersonViewModel by viewModel()
    private lateinit var binding: DetailedActorFragmentLayoutBinding
    private lateinit var personMovieAdapter: PersonMovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.detailed_actor_fragment_layout, container, false)
        binding = DetailedActorFragmentLayoutBinding.bind(view)
        personMovieAdapter = PersonMovieAdapter(arrayListOf())
        personViewModel.id = requireArguments().getInt(ID)
        personViewModel.loadPerson()
        personViewModel.loadPersonMovies()
        setupActorObserver()
        setupActorMovieObserver()
        return view
    }

    private fun setupActorObserver() {
        personViewModel.persons.observe(requireActivity(), {
            when (it.status) {
                Status.LOADING -> {
                    binding.detailedActorPageElementsGroup.visibility = View.GONE
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.refreshButton.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.detailedActorPageElementsGroup.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                    binding.refreshButton.visibility = View.GONE
                    it.data?.let { resourceData -> setupUI(resourceData) }

                }
                Status.ERROR -> {
                    binding.detailedActorPageElementsGroup.visibility = View.GONE
                    binding.progressCircular.visibility = View.GONE
                    binding.refreshButton.visibility = View.VISIBLE
                    Toast.makeText(
                        requireActivity(),
                        R.string.no_internet_connection,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun setupUI(person: Person) {
        binding.actorName.text = person.name
        binding.gender.text = if (person.gender == 2) "man" else "woman"
        binding.birthdate.text = if (person.birthday == null) "unknown" else person.birthday
        binding.deathdate.text = if (person.deathday == null) "alive" else person.deathday
        binding.department.text = person.knownForDepartment
        binding.placeOfBirth.text = person.placeOfBirth
        binding.shortDescriptions.text = person.biography
        Glide.with(binding.root)
            .load("https://image.tmdb.org/t/p/original/" + person.profilePath)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.photoProgressCircular.visibility = View.VISIBLE
                    binding.actorPhoto.visibility = View.GONE

                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.photoProgressCircular.visibility = View.GONE
                    binding.actorPhoto.visibility = View.VISIBLE
                    return false
                }

            })
            .thumbnail(0.3f)
            .into(binding.actorPhoto)
        val recyclerView = binding.personRecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = personMovieAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL
            )
        )
        personMovieAdapter.itemClickListener = object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val movie = personMovieAdapter.movies[position]
                val bundle = Bundle()
                movie.id?.let { bundle.putInt(ID, it) }
                if (NetworkHelper(requireActivity()).isNetworkConnected()) {
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_detailedActorFragment2_to_detailedMovieFragment2, bundle)
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.no_internet_connection,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupActorMovieObserver() {
        val personMovieList = ArrayList<PersonMovies>()
        personViewModel.roles.observe(requireActivity(), {
            when (it.status) {
                Status.LOADING -> {
                    //может быть потом прогресс бар сюда поставлю
                }
                Status.SUCCESS -> {
                    it.data?.let { personRolesList ->
                        personRolesList.forEach { personRole ->
                            val personMovie = PersonMovies(
                                personRole.id,
                                personRole.character,
                                personRole.posterPath,
                                personRole.title,
                                personRole.voteAverage,
                                personRole.releaseDate
                            )
                            if (!personMovieList.contains(personMovie)) {
                                personMovieList.add(personMovie)
                            }
                        }
                    }

                }
                Status.ERROR -> {
                    //может быть потом что-то сюда поставлю
                }
            }
            refreshAdapterData(personMovieList)
        })
        personViewModel.crewsMember.observe(requireActivity(), {
            when (it.status) {
                Status.LOADING -> {
                    //может быть потом прогресс бар сюда поставлю
                }
                Status.SUCCESS -> {
                    it.data?.let { personCrewWorkList ->
                        personCrewWorkList.forEach { personCrewWork ->
                            val personMovie = PersonMovies(
                                personCrewWork.id,
                                personCrewWork.job,
                                personCrewWork.posterPath,
                                personCrewWork.title,
                                personCrewWork.voteAverage,
                                personCrewWork.releaseDate
                            )
                            if (!personMovieList.contains(personMovie)) {
                                personMovieList.add(personMovie)
                            }

                        }
                    }

                }
                Status.ERROR -> {
                    //может быть потом что-то сюда поставлю
                }
            }
            refreshAdapterData(personMovieList)
        })
    }

    private fun refreshAdapterData(personMovieList: java.util.ArrayList<PersonMovies>) {
        personMovieAdapter.movies.addAll(personMovieList)
        personMovieAdapter.notifyDataSetChanged()
    }
}