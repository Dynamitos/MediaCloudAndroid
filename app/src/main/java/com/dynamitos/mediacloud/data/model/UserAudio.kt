package com.dynamitos.mediacloud.data.model

import android.os.Parcel
import android.os.Parcelable
import com.dynamitos.mediacloud.data.Util
import com.dynamitos.mediacloud.network.BASE_URL
import com.google.gson.annotations.SerializedName
import java.net.URLDecoder

class UserAudio constructor(parcel: Parcel) : Parcelable {
        @SerializedName("name") val name: String? = parcel.readString()
                get(){
                        if(field != null){
                                return field.removeSuffix(".mp3")
                        }
                        return "Unknown"
                }
        @SerializedName("artworkURL") val artURL: String? = parcel.readString()
                get() = "${BASE_URL}$field"
        @SerializedName("songURL") val songURL: String? = parcel.readString()
                get() = "${BASE_URL}$field"
        @SerializedName("artistName") val artistName: String? = parcel.readString()
                get() {
                        return if(field.isNullOrEmpty()) {
                                "Unknown"
                        } else {
                                field
                        }
                }
        @SerializedName("albumName") val albumName: String? = parcel.readString()
                get() {
                        return if(field.isNullOrEmpty()) {
                                "Unknown"
                        } else {
                                field
                        }
                }
        @SerializedName("length") val songLength: Int = parcel.readInt()

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(name)
                parcel.writeString(artURL)
                parcel.writeString(songURL)
                parcel.writeString(artistName)
                parcel.writeString(albumName)
                parcel.writeInt(songLength)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<UserAudio> {
                override fun createFromParcel(parcel: Parcel): UserAudio {
                        return UserAudio(parcel)
                }

                override fun newArray(size: Int): Array<UserAudio?> {
                        return arrayOfNulls(size)
                }

                fun createFromParams(name: String, artURL: String, songURL: String,
                artistName: String, albumName: String, songLength: Int) : UserAudio{
                        val parcel = Parcel.obtain()
                        parcel.writeString(name)
                        parcel.writeString(artURL)
                        parcel.writeString(songURL)
                        parcel.writeString(artistName)
                        parcel.writeString(albumName)
                        parcel.writeInt(songLength)
                        parcel.setDataPosition(0)
                        val ret = UserAudio(parcel)
                        parcel.recycle()
                        return ret
                }
        }
}