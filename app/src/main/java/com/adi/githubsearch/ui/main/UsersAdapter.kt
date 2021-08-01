package com.adi.githubsearch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adi.githubsearch.R
import com.adi.githubsearch.api.response.Item
import com.adi.githubsearch.databinding.UserItemBinding
import com.bumptech.glide.Glide

/**
 * Adapter for the user list.
 */
class UsersAdapter(
    private var users: ArrayList<Item> = arrayListOf()
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users[position]

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.user = item
            Glide.with(binding.image)
                .load(item.avatar_url)
                .centerInside()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.image)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UserItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = users.size
}
