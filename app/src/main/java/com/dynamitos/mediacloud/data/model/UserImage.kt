package com.dynamitos.mediacloud.data.model

import com.google.gson.annotations.SerializedName

data class UserImage (
        @SerializedName("name")
        val name: String,
        @SerializedName("imageURL")
        val imgURL: String,
        @SerializedName("thumbnailURL")
        val thumbnailURL: String,
        @SerializedName("width")
        val width: Int,
        @SerializedName("height")
        val height: Int)