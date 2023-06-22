package com.dynamitos.mediacloud.data.model

import android.os.Parcel
import android.os.Parcelable
import com.dynamitos.mediacloud.data.Util
import com.dynamitos.mediacloud.network.BASE_URL
import com.google.gson.annotations.SerializedName
import java.net.URLDecoder

class UserImage constructor(parcel: Parcel) : Parcelable {
        @SerializedName("name")
        val name: String? = URLDecoder.decode(parcel.readString(), "UTF-8")
        @SerializedName("imageURL")
        val imgURL: String? = parcel.readString()
                get() = "${BASE_URL}/$field"
        @SerializedName("thumbnailURL")
        val thumbnailURL: String? = URLDecoder.decode(parcel.readString(), "UTF-8")
        @SerializedName("width")
        val width: Int = parcel.readInt()
        @SerializedName("height")
        val height: Int = parcel.readInt()

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(name)
                parcel.writeString(imgURL)
                parcel.writeString(thumbnailURL)
                parcel.writeInt(width)
                parcel.writeInt(height)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<UserImage> {
                override fun createFromParcel(parcel: Parcel): UserImage {
                        return UserImage(parcel)
                }

                override fun newArray(size: Int): Array<UserImage?> {
                        return arrayOfNulls(size)
                }
        }
}