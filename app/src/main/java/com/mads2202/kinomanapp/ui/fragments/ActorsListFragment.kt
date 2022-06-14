package com.mads2202.kinomanapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mads2202.kinomanapp.App
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.PersonsListFragmentBinding
import com.mads2202.kinomanapp.ui.viewModels.PersonListViewModel
import com.mads2202.kinomanapp.common.ID
import com.mads2202.kinomanapp.common.NetworkHelper
import com.mads2202.kinomanapp.model.jsonModel.personModel.Person
import com.mads2202.kinomanapp.ui.adapters.MovieAdapter
import com.mads2202.kinomanapp.ui.adapters.PersonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActorsListFragment : Fragment() {
    @Inject
    lateinit var factory: PersonListViewModel.PersonListViewModelFactory
    private val viewModel: PersonListViewModel by viewModels { factory }
    var binding: PersonsListFragmentBinding? = null
    lateinit var personAdapter: PersonAdapter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.persons_list_fragment, container, false)
        binding = PersonsListFragmentBinding.bind(view)
        personAdapter = PersonAdapter()
        personAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        if (NetworkHelper(requireContext()).isNetworkConnected()) {
            loadData()
            setupUI()
        } else {
            setupNoConnectionUI()
        }

        return view
    }

    private fun setupNoConnectionUI() {
        binding?.let { binding ->
            binding.refreshButton.visibility = View.VISIBLE
            binding.personRecyclerView.visibility = View.GONE
            binding.refreshButton.setOnClickListener {
                if (NetworkHelper(requireContext()).isNetworkConnected()) {
                    loadData()
                    binding.refreshButton.visibility = View.GONE
                    binding.personRecyclerView.visibility = View.VISIBLE
                    setupUI()
                }
            }
        }
    }

    private fun loadData() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.persons
                .collect { pagingData ->
                    personAdapter.submitData(pagingData)
                }

        }
    }

    private fun setupUI() {
        binding?.let {
            val recyclerView = it.personRecyclerView
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
            recyclerView.adapter = personAdapter
            personAdapter.itemClickListener = object : MovieAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    if (NetworkHelper(requireContext()).isNetworkConnected()) {
                        val person = personAdapter.snapshot()[position] as Person
                        val bundle = Bundle()
                        bundle.putInt(ID, person.id)
                        Navigation.findNavController(it.root)
                            .navigate(
                                R.id.action_actorsListFragment_to_detailedActorFragment2,
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}