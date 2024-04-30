package com.example.pages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.entities.Reels
import com.example.pages.databinding.ReelsDesignBinding

class ReelsAdapter : ListAdapter<Reels, ReelsAdapter.MyViewHolder>(ReelsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ReelsDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ReelsDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Reels) {
            // Set the video URL from the Reels object
            val videoUrl = item.video
            binding.reelsVideo.setVideoPath(videoUrl)
            binding.reelsVideo.setOnPreparedListener { mp ->
                mp.start()
            }
        }
    }

    class ReelsDiffCallback : DiffUtil.ItemCallback<Reels>() {
        override fun areItemsTheSame(oldItem: Reels, newItem: Reels): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Reels, newItem: Reels): Boolean {
            return oldItem == newItem
        }
    }
}
