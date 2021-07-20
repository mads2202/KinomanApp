package com.mads2202.kinomanapp.util.adapters

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
import com.mads2202.kinomanapp.databinding.PeopleListItemLayoutBinding
import com.mads2202.kinomanapp.model.jsonModel.personModel.Person

class PersonAdapter(val persons: ArrayList<Person>) :
    RecyclerView.Adapter<PersonAdapter.PersonVH>() {
    lateinit var itemClickListener: MovieAdapter.OnItemClickListener

    inner class PersonVH(val binding: PeopleListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(it, bindingAdapterPosition)
            }
        }

        fun bind(person: Person) {
            binding.actorDepartment.text = person.knownForDepartment
            binding.actorName.text = person.name
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/original/" + person.profilePath)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {

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

                        binding.actorPhoto.visibility = View.VISIBLE
                        return false
                    }

                })
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
        holder.bind(persons[position])
    }

    override fun getItemCount(): Int {
        return persons.size
    }
}