package com.hegunhee.sample_playground.feature.secretkeypad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hegunhee.sample_playground.databinding.ItemKeypadBinding

class KeypadAdapter() : ListAdapter<String,KeypadAdapter.KeypadViewHolder>(diff_util) {

    inner class KeypadViewHolder(private val binding : ItemKeypadBinding) : ViewHolder(binding.root) {
        fun bind(keypad : String) {
            binding.keypadText = keypad
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeypadViewHolder {
        return KeypadViewHolder(ItemKeypadBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: KeypadViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private object diff_util : ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }
}