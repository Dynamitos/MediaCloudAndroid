package com.dynamitos.mediacloud.data.model

import android.os.Parcel
import android.os.Parcelable
import com.dynamitos.mediacloud.data.Util
import com.google.gson.annotations.SerializedName

class UserImage constructor(parcel: Parcel) : Parcelable {
        @SerializedName("name")
        val name: String? = parcel.readString()
        @SerializedName("imageURL")
        val imgURL: String? = parcel.readString()
                get() = "${Util.getBaseUrl()}$field"
        @SerializedName("thumbnailURL")
        val thumbnailURL: String? = parcel.readString()
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