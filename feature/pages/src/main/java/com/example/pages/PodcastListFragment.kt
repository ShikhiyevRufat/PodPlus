package com.example.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.entities.Categories
import com.example.entities.Podcasts
import com.example.pages.adapter.PodcastListAdapter
import com.example.pages.adapter.PodcastsAdapter
import com.example.pages.databinding.FragmentPodcastListBinding
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PodcastListFragment : Fragment() {
    private lateinit var binding: FragmentPodcastListBinding
    private lateinit var podcastListAdapter: PodcastListAdapter

    companion object {
        lateinit var categories: Categories
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPodcastListBinding.inflate(layoutInflater)

        Glide.with(binding.podcastIm)
            .load(categories.imageurl)
            .apply(RequestOptions().transform(RoundedCorners(32)))
            .into(binding.podcastIm)

        podcastsRecycleView()

        return binding.root
    }

    private fun podcastsRecycleView(){
        podcastListAdapter = PodcastListAdapter(categories.podcasts)
        binding.episodesRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.episodesRecycler.adapter = podcastListAdapter
    }

}

