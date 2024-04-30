package com.example.pages.ExoPlayer

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.entities.Podcasts

object MyExoplayer {

    private var currentAudio: Podcasts? = null
    private var exoPlayer : ExoPlayer? = null

    fun getInstance() : ExoPlayer? {
        return exoPlayer
    }

    fun getCurrentAudios(): Podcasts? {
        return currentAudio
    }

    fun startPlayer (context: Context, audio : Podcasts){

        if (exoPlayer == null)
            exoPlayer = ExoPlayer.Builder(context).build()

        if (currentAudio != audio){
            currentAudio = audio
        }

        currentAudio = audio
        currentAudio?.audio?.apply {
            val mediaItem = MediaItem.fromUri(this)
            exoPlayer?.setMediaItem(mediaItem)
            exoPlayer?.prepare()
            exoPlayer?.play()
        }

    }


}
