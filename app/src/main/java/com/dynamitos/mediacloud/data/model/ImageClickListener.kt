package com.dynamitos.mediacloud.data.model

import android.widget.ImageView
import com.dynamitos.mediacloud.data.model.UserImage

interface ImageClickListener {
    fun onImageClicked(position: Int, image: UserImage, view: ImageView)
}