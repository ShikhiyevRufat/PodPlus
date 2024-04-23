package com.example.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pages.ExoPlayer.MyExoplayer
import com.example.pages.databinding.FragmentPodcastPlayerBinding

class PodcastPlayerFragment : Fragment() {

    private lateinit var binding: FragmentPodcastPlayerBinding
    private lateinit var exoPlayer: ExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPodcastPlayerBinding.inflate(layoutInflater)

        MyExoplayer.getCurrentAudios()?.apply {
            binding.podTxt.text = name
            Glide.with(binding.podIm)
                .load(imageurl)
                .apply(RequestOptions().transform(RoundedCorners(32)))
                .into(binding.podIm)
            exoPlayer = MyExoplayer.getInstance()!!
//            binding.playeraudio.player = exoPlayer
            binding.playPodcast.setOnClickListener {
                exoPlayer
            }
        }



        return binding.root
    }

}