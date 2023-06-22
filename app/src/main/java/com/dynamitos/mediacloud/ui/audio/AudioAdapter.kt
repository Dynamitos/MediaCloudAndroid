package com.dynamitos.mediacloud.ui.audio

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.LoginRepository
import com.dynamitos.mediacloud.data.model.AudioClickListener
import com.dynamitos.mediacloud.data.model.UserAudio

class AudioAdapter(private val audioList: List<UserAudio>,
                   private val listener: AudioClickListener
) :
    RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_audio, parent, false)
        return AudioViewHolder(view)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val audio = audioList[position]

        val glideUrl = GlideUrl(audio.artURL, LazyHeaders.Builder()
            .addHeader("Authorization", LoginRepository.getInstance().user!!.authToken)
            .build())

        val circularProgressDrawable = CircularProgressDrawable(holder.root.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(holder.galleryImageView.context)
            .load(glideUrl)
            //.thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(circularProgressDrawable)
            .error(ColorDrawable(Color.RED))
            .fallback(ColorDrawable(Color.GRAY))
            .into(holder.galleryImageView)

        ViewCompat.setTransitionName(holder.galleryImageView, audio.name)

        holder.root.setOnClickListener {
            listener.onAudioClicked(
                holder.adapterPosition,
                audio,
                holder.galleryImageView
            )
        }

        holder.songNameView.text = audio.name
        holder.artistNameView.text = audio.artistName
        holder.albumNameView.text = audio.albumName
    }

    override fun getItemCount(): Int {
        return audioList.size
    }

    inner class AudioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: LinearLayout = view.findViewById(R.id.audioGalleryRoot)
        val galleryImageView: ImageView = view.findViewById(R.id.audioThumbnail)
        val songNameView: TextView = view.findViewById(R.id.songName)
        val artistNameView: TextView = view.findViewById(R.id.artistName)
        val albumNameView: TextView = view.findViewById(R.id.albumName)
    }
}