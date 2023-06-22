package com.dynamitos.mediacloud.data.model

import android.widget.ImageView
import com.dynamitos.mediacloud.data.model.UserImage

interface AudioClickListener {
    fun onAudioClicked(position: Int, image: UserImage, view: ImageView)
}