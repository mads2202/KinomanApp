package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.ListFragmentBinding
import com.mads2202.kinomanapp.databinding.PersonsListFragmentBinding
import com.mads2202.kinomanapp.model.jsonModel.personModel.Person
import com.mads2202.kinomanapp.ui.viewModels.PersonListViewModel
import com.mads2202.kinomanapp.ui.viewModels.PersonViewModel
import com.mads2202.kinomanapp.util.ID
import com.mads2202.kinomanapp.util.adapters.MovieAdapter
import com.mads2202.kinomanapp.util.adapters.PersonAdapter
import com.mads2202.kinomanapp.util.networkUtil.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class ActorsListFragment : Fragment() {
    private val viewModel: PersonListViewModel by viewModel()
    lateinit var binding: PersonsListFragmentBinding
    lateinit var personAdapter: PersonAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.persons_list_fragment, container, false)
        binding = PersonsListFragmentBinding.bind(view)
        personAdapter = PersonAdapter(ArrayList())
        personAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        loadData()
        setupUI()
        return view
    }

    private fun loadData() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.persons.filter { response -> response.isSuccessful && !response.body()?.biography.isNullOrBlank() && response.body()?.profilePath != null }
                .collect { response ->
                    response.body()?.let { personAdapter.persons.add(it) }
                    withContext(Dispatchers.Main) {
                        if (personAdapter.persons.size == 20 || personAdapter.persons.size == 50) {
                            personAdapter.notifyDataSetChanged()
                        }
                    }
                }
            withContext(Dispatchers.Main) {
                personAdapter.notifyDataSetChanged()
            }
        }
    }


    fun setupUI() {
        val recyclerView = binding.personRecyclerView
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
                val person = personAdapter.persons[position]
                val bundle = Bundle()
                person.id?.let { bundle.putInt(ID, it) }

                Navigation.findNavController(binding.root)
                    .navigate(
                        R.id.action_actorsListFragment_to_detailedActorFragment2,
                        bundle
                    )
            }
        }
    }
}