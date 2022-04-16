package com.example.a120422.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a120422.Item
import com.example.a120422.databinding.ItemCountBinding
import com.example.a120422.databinding.ItemLoadingBinding

class CountAdapter(
    context: Context
) : ListAdapter<Item, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)){
            is Item.Count -> TYPE_COUNTER
            Item.Loading -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            TYPE_COUNTER -> {
                CountViewHolder(
                    binding = ItemCountBinding.inflate(layoutInflater, parent, false)
                )
            }
            TYPE_LOADING ->{
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> error("Incorrect viewType = $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val countViewHolder = holder as? CountViewHolder ?: return
        val item = getItem(position) as? Item.Count ?: return
        countViewHolder.bind(item)

    }

    companion object {

        private const val TYPE_COUNTER = 0
        private const val TYPE_LOADING = 1

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Item>(){
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class CountViewHolder(
    private val binding: ItemCountBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item.Count) {
        binding.count.text = item.value.toString()
    }
}

class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)