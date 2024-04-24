package com.example.pages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pages.databinding.CategoryItemDesignBinding

class MyPlayListAdapter(private val data: List<String>) :
    RecyclerView.Adapter<MyPlayListAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: CategoryItemDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData() {
            // Bind data to views if needed
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CategoryItemDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData()
    }
}
