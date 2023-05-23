package com.dynamitos.mediacloud.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.model.ImageClickListener
import com.dynamitos.mediacloud.data.model.UserImage

class GalleryAdapter(private val imageList: List<UserImage>,
                     private val listener: ImageClickListener
) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_image, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val image = imageList[position]

        Glide.with(holder.galleryImageView.context)
            .load(image.imgURL)
            //.thumbnail(0.5f)
            //.diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.galleryImageView)

        ViewCompat.setTransitionName(holder.galleryImageView, image.name)

        holder.galleryImageView.setOnClickListener {
            listener.onImageClicked(
                holder.adapterPosition,
                image,
                holder.galleryImageView
            )
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val galleryImageView: ImageView = view.findViewById(R.id.galleryImage)
    }
}