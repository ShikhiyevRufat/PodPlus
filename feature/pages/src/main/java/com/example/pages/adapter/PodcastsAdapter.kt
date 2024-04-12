package com.example.pages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.entities.Podcasts
import com.example.pages.databinding.PodcastListDesignBinding

class PodcastsAdapter(private val podcastList: List<Podcasts>): RecyclerView.Adapter<PodcastsAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: PodcastListDesignBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bindData(podcast: Podcasts){
                binding.podcastName.text = podcast.name
                Glide.with(binding.podcastImages)
                    .load(podcast.imageurl)
                    .apply(RequestOptions().transform(RoundedCorners(20)))
                    .into(binding.podcastImages)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PodcastListDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return podcastList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(podcastList[position])
    }
}