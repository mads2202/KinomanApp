package com.mads2202.kinomanapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.ListFragmentBinding
import com.mads2202.kinomanapp.ui.viewModels.PersonViewModel
import com.mads2202.kinomanapp.util.adapters.PersonAdapter
import com.mads2202.kinomanapp.util.networkUtil.Status
import org.koin.android.viewmodel.ext.android.viewModel

class ActorsListFragment : Fragment() {
    /*val viewModel: PersonViewModel by viewModel()
    lateinit var binding: ListFragmentBinding
    lateinit var personAdapter: PersonAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        binding = ListFragmentBinding.bind(view)
        personAdapter = PersonAdapter(ArrayList())
        setupObserver()
        setupUI()
        return view
    }

    fun setupObserver() {
        viewModel.persons.observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { it1 -> personAdapter.persons.addAll(it1) }
                    personAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    fun setupUI() {
        val recyclerView = binding.movieListFragmentRecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView.adapter = personAdapter
    }*/
}