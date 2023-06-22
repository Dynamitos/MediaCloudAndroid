package com.dynamitos.mediacloud.data.model

import android.media.MediaPlayer
import android.widget.LinearLayout
import android.widget.TextView
import com.dynamitos.mediacloud.R
import java.io.IOException

class AudioPlayer private constructor() {
    companion object {
        private var instance: AudioPlayer? = null

        fun getInstance(): AudioPlayer {
            if (instance == null) {
                instance = AudioPlayer()
            }
            return instance!!
        }
    }

    private var mediaPlayer: MediaPlayer? = null
    private var isPaused: Boolean = false
    private var currentUrl: String? = null

    private var fieldsInitialized = false
    private var songField : TextView? = null
    private var albumField : TextView? = null
    private var artistField : TextView? = null
    fun initTextFields(parent: LinearLayout){
        fieldsInitialized = true
        songField = parent.findViewById<TextView>(R.id.songNameView)
        albumField = parent.findViewById<TextView>(R.id.albumNameView)
        artistField = parent.findViewById<TextView>(R.id.artistNameView)
    }

    fun toggle(url: String, name: String, artist: String, album: String) {
        if (isPaused && currentUrl == url) {
            resume()
        } else if(currentUrl == url) {
            pause()
        } else {
            play(url, name, artist, album)
        }
    }

    fun toggle() {
        if(currentUrl == null) {
            return
        } else if(isPaused) {
            resume()
        } else {
            pause()
        }
    }

    fun play(url: String, name: String, artist: String, album: String) {
        stop()

        try {
            if(fieldsInitialized){
                songField?.text = name
                albumField?.text = album
                artistField?.text = artist

            }
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
            currentUrl = url
            isPaused = false
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun resume(){
        mediaPlayer?.let {
            if (isPaused) {
                it.start()
                isPaused = false
            }
        }
    }

    fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isPaused = true
            }
        }
    }

    fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying || isPaused) {
                it.stop()
            }
            it.reset()
            it.release()
            mediaPlayer = null
            isPaused = false
            currentUrl = null
        }
    }
}