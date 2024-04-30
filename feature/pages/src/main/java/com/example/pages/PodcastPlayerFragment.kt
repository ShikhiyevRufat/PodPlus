package com.example.pages

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.entities.Categories
import com.example.pages.ExoPlayer.MyExoplayer
import com.example.pages.databinding.FragmentPodcastPlayerBinding

class PodcastPlayerFragment : Fragment() {

    private lateinit var binding: FragmentPodcastPlayerBinding
    private lateinit var exoPlayer: ExoPlayer
    private var isPlaying = false
    private lateinit var seekBar: SeekBar
    private lateinit var runnable: Runnable
    private var handler = Handler()
    private lateinit var podcastList: List<Categories>
    private var currentPodcastIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPodcastPlayerBinding.inflate(layoutInflater)
        seekBar = binding.secondPodcast

        MyExoplayer.getCurrentAudios()?.apply {
            binding.podTxt.text = name
            Glide.with(binding.podIm)
                .load(imageurl)
                .apply(RequestOptions().transform(RoundedCorners(32)))
                .into(binding.podIm)
            exoPlayer = MyExoplayer.getInstance()!!
            seekBar.progress = 0
//            binding.playeraudio.player = exoPlayer
            binding.playPodcast.setOnClickListener {
                if (isPlaying) {
                    exoPlayer.pause();
                    isPlaying = false;
                    binding.playPodcast.setImageResource(R.drawable.podcastplayer); // Change icon to play icon
                } else {
                    exoPlayer.play();
                    isPlaying = true;
                    binding.playPodcast.setImageResource(R.drawable.podcast_pause); // Change icon to pause icon
                }

            }


        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Implementation for progress change event
                if (fromUser) {
                    exoPlayer.seekTo(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Optional: Implement any actions you want to perform when the user starts tracking touch events
                // Leave empty for now if you don't need to perform any action
                // Log.d("PodcastPlayerFragment", "Seekbar tracking started")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Optional: Implement any actions you want to perform when the user stops tracking touch events
                // Leave empty for now if you don't need to perform any action
                // Log.d("PodcastPlayerFragment", "Seekbar tracking stopped")
            }
        })
        seekBar.max = exoPlayer.duration.toInt()
        runnable = Runnable {
            if (exoPlayer.duration > 0) { // Check if duration is available to prevent division by zero
                val currentPosition = exoPlayer.currentPosition.toInt()
                val totalDuration = exoPlayer.duration.toInt()
                seekBar.max = totalDuration
                seekBar.progress = currentPosition
            }
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        binding.nextRight.setOnClickListener {
        }

        binding.nextLeft.setOnClickListener {
        }


        return binding.root
    }



}