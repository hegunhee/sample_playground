package com.hegunhee.sample_playground.feature.secretkeypad.log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hegunhee.sample_playground.R
import com.hegunhee.sample_playground.databinding.ItemLogCheckBinding
import com.hegunhee.sample_playground.databinding.ItemLogRegisterBinding

class LogAdapter() : ListAdapter<String, LogAdapter.LogViewHolder>(diff_util) {

    sealed class LogViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindView(log : String)
    }

    class RegisterLogViewHolder(private val binding : ItemLogRegisterBinding) : LogViewHolder(binding.root) {

        override fun bindView(log: String) {
            binding.log = log
        }

    }

    class CheckLogViewHolder(private val binding : ItemLogCheckBinding) : LogViewHolder(binding.root) {

        override fun bindView(log: String) {
            binding.log = log
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        return when(viewType) {
            R.layout.item_log_register -> {
                RegisterLogViewHolder(ItemLogRegisterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            R.layout.item_log_check -> {
                CheckLogViewHolder(ItemLogCheckBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }else -> { throw IllegalArgumentException()}
        }
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        val log = getItem(position)
        return when(log.split(" ")[0]) {
            "등록" -> {
                R.layout.item_log_register
            }
            "확인" -> {
                R.layout.item_log_check
            }
            else -> { throw IllegalArgumentException()}
        }
    }
    companion object {
        val diff_util = object : DiffUtil.ItemCallback<String>()  {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }
}