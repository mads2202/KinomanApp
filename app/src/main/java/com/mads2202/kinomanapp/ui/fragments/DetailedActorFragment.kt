package com.mads2202.kinomanapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mads2202.kinomanapp.App
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.DetailedActorFragmentLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.personModel.Person
import com.mads2202.kinomanapp.model.jsonModel.personModel.PersonMovies
import com.mads2202.kinomanapp.ui.viewModels.PersonViewModel
import com.mads2202.kinomanapp.common.ID
import com.mads2202.kinomanapp.common.LOCATION_NAME
import com.mads2202.kinomanapp.ui.adapters.MovieAdapter
import com.mads2202.kinomanapp.ui.adapters.PersonMovieAdapter
import com.mads2202.kinomanapp.common.NetworkHelper
import com.mads2202.kinomanapp.common.Status
import javax.inject.Inject

class DetailedActorFragment : Fragment() {
    @Inject
    lateinit var factory: PersonViewModel.PersonViewModelFactory
    private val personViewModel: PersonViewModel by viewModels { factory }
    private var binding: DetailedActorFragmentLayoutBinding? = null
    private lateinit var personMovieAdapter: PersonMovieAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.detailed_actor_fragment_layout, container, false)
        binding = DetailedActorFragmentLayoutBinding.bind(view)
        personMovieAdapter = PersonMovieAdapter(arrayListOf())
        personViewModel.id = requireArguments().getInt(ID)
        if (NetworkHelper(requireContext()).isNetworkConnected()) {
            personViewModel.loadPerson()
            personViewModel.loadPersonMovies()
            setupActorObserver()
            setupActorMovieObserver()
        } else {
            setupNoConnectionUI()
        }
        return view
    }

    private fun setupActorObserver() {
        binding?.let { binding ->
            personViewModel.persons.observe(viewLifecycleOwner, {
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
                        it.data?.let { resourceData -> bindPerson(resourceData) }

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
    }

    private fun setupNoConnectionUI() {
        if (!NetworkHelper(requireContext()).isNetworkConnected()) {
            binding?.let { binding ->
                binding.detailedActorPageElementsGroup.visibility = View.GONE
                binding.refreshButton.visibility = View.VISIBLE
            }
        }
        setupRefreshButtonClickListener()
    }

    private fun bindPerson(person: Person) {
        setupRefreshButtonClickListener()
        if (NetworkHelper(requireContext()).isNetworkConnected()) {
            binding?.let { binding ->
                binding.actorName.text = person.name
                binding.gender.text =
                    if (person.gender == 2) getString(R.string.gender_man) else getString(
                        R.string.gender_woman
                    )
                binding.birthdate.text = if (person.birthday == null) getString(
                    R.string.unknown
                ) else person.birthday
                binding.deathdate.text =
                    if (person.deathday == null) getString(R.string.alive) else person.deathday
                binding.department.text = person.knownForDepartment
                binding.placeOfBirth.text =
                    if (person.placeOfBirth != null) person.placeOfBirth else getString(
                        R.string.unknown
                    )
                binding.placeOfBirth.setOnClickListener {
                    if (NetworkHelper(requireContext()).isNetworkConnected()) {
                        if (binding.placeOfBirth.text != getString(
                                R.string.unknown
                            )
                        ) {
                            val bundle = Bundle()
                            bundle.putString(LOCATION_NAME, person.placeOfBirth)
                            Navigation.findNavController(binding.root)
                                .navigate(
                                    R.id.action_detailedActorFragment2_to_mapsFragment,
                                    bundle
                                )
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            R.string.no_internet_connection,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                binding.shortDescriptions.text = person.biography
            }
            loadActorPhoto(person)
            setActorMovieClickListener()
        } else {
            binding?.let {
                it.detailedActorPageElementsGroup.visibility = View.GONE
                it.refreshButton.visibility = View.VISIBLE
            }
        }
    }

    private fun setupActorMovieObserver() {
        val personMovieList = ArrayList<PersonMovies>()
        personViewModel.roles.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    var personMovie: PersonMovies
                    it.data?.let { personRolesList ->
                        personRolesList.forEach { personRole ->
                            personMovie = PersonMovies(
                                personRole.id,
                                personRole.character,
                                personRole.posterPath,
                                personRole.title,
                                personRole.voteAverage,
                                personRole.releaseDate
                            )
                            if (!personMovieList.contains(personMovie) ||
                                personMovie.posterPath != null
                            ) {
                                personMovieList.add(personMovie)
                            }
                        }
                    }

                }
                Status.ERROR -> {
                }
            }
            refreshAdapterData(personMovieList)
        })
        personViewModel.crewsMember.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
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
                            if (!personMovieList.contains(personMovie) ||
                                personMovie.posterPath != null
                            ) {
                                personMovieList.add(personMovie)
                            }

                        }
                    }

                }
                Status.ERROR -> {
                }
            }
            refreshAdapterData(personMovieList)
        })
    }

    private fun refreshAdapterData(personMovieList: java.util.ArrayList<PersonMovies>) {
        personMovieList.forEach { movie ->
            if (!personMovieAdapter.movies.contains(movie)) {
                personMovieAdapter.movies.add(movie)
            }
        }
        personMovieAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupRefreshButtonClickListener() {
        binding?.let { binding ->
            binding.refreshButton.setOnClickListener {
                if (NetworkHelper(requireContext()).isNetworkConnected()) {
                    personViewModel.loadPerson()
                    personViewModel.loadPersonMovies()
                    binding.refreshButton.visibility = View.GONE
                    binding.detailedActorPageElementsGroup.visibility = View.VISIBLE
                    setupActorObserver()
                    setupActorMovieObserver()
                }
            }
        }
    }

    private fun loadActorPhoto(person: Person) {
        binding?.let { binding ->
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/original/" + person.profilePath)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.image_loading)
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
        }
    }

    private fun setActorMovieClickListener() {
        binding?.let { binding ->
            personMovieAdapter.itemClickListener = object : MovieAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val movie = personMovieAdapter.movies[position]
                    val bundle = Bundle()
                    bundle.putInt(ID, movie.id)
                    if (NetworkHelper(requireActivity()).isNetworkConnected()) {
                        Navigation.findNavController(binding.root)
                            .navigate(
                                R.id.action_detailedActorFragment2_to_detailedMovieFragment2,
                                bundle
                            )
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
    }
}