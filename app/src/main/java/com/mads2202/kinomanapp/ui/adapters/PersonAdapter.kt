package com.mads2202.kinomanapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mads2202.kinomanapp.R
import com.mads2202.kinomanapp.databinding.PeopleListItemLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.personModel.Person

class PersonAdapter :
    PagingDataAdapter<Person, PersonAdapter.PersonVH>(DataDifferntiator) {
    lateinit var itemClickListener: MovieAdapter.OnItemClickListener

    inner class PersonVH(val binding: PeopleListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(it, bindingAdapterPosition)
            }
        }

        fun bind(person: Person) {
            binding.actorDepartment.text = person.knownForDepartment
            binding.actorName.text = person.name
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/original/" + person.profilePath)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.image_loading)
                .thumbnail(0.3f)
                .into(binding.actorPhoto)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.people_list_item_layout, parent, false)
        val binding = PeopleListItemLayoutBinding.bind(view)
        return PersonVH(binding)
    }

    override fun onBindViewHolder(holder: PersonVH, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    object DataDifferntiator : DiffUtil.ItemCallback<Person>() {

        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }
}