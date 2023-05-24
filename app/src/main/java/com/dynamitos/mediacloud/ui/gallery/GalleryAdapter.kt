package com.dynamitos.mediacloud.ui.gallery

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import com.bumptech.glide.module.AppGlideModule
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.GalleryGlideModule
import com.dynamitos.mediacloud.data.LoginRepository
import com.dynamitos.mediacloud.data.model.ImageClickListener
import com.dynamitos.mediacloud.data.model.LoggedInUser
import com.dynamitos.mediacloud.data.model.UserImage
import com.dynamitos.mediacloud.network.APIClient

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

        val glideUrl = GlideUrl(image.imgURL, LazyHeaders.Builder()
            .addHeader("Authorization", LoginRepository.getInstance().user!!.authToken)
            .build())

        val circularProgressDrawable = CircularProgressDrawable(holder.galleryImageView.context)
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