package com.hegunhee.sample_playground.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hegunhee.sample_playground.databinding.ItemMainNavBinding

class NavItemAdapter(private val actionHandler : MainNavItemActionHandler) : ListAdapter<MainNavItem,NavItemAdapter.NavItemViewHolder>(diffUtil) {

    inner class NavItemViewHolder(private val binding : ItemMainNavBinding) : ViewHolder(binding.root) {

        fun bind(navItem : MainNavItem) {
            binding.actionHandler = actionHandler
            binding.navItem = navItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavItemViewHolder {
        return NavItemViewHolder(ItemMainNavBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NavItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private object diffUtil : DiffUtil.ItemCallback<MainNavItem>() {
            override fun areItemsTheSame(oldItem: MainNavItem, newItem: MainNavItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: MainNavItem, newItem: MainNavItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}