package com.dynamitos.mediacloud.data.model

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private fun getHeader() : HashMap<String, String>{
        val headers = HashMap<String, String>()
        headers["Authorization"] = LoginRepository.getInstance().user!!.authToken
        return headers
    }

    fun toggle(url: String, name: String, artist: String?, album: String?, context: Context) {
        if (isPaused && currentUrl == url) {
            resume()
        } else if(currentUrl == url) {
            pause()
        } else {
            play(url, name, artist, album, context)
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

    fun play(url: String, name: String, artist: String?, album: String?, context: Context) {
        stop()


            if(fieldsInitialized){
                songField?.text = name
                albumField?.text = album
                artistField?.text = artist
            }
            isPaused = false

            mediaPlayer = MediaPlayer()
            try {
                mediaPlayer?.setDataSource(context, Uri.parse(url), getHeader())
                mediaPlayer?.prepare()
                mediaPlayer?.start()
                currentUrl = url
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (isPaused){
                pause()
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
        isPaused = true
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
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