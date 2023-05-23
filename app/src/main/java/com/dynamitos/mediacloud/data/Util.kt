package com.dynamitos.mediacloud.data

import android.os.Handler
import android.os.Looper
import com.dynamitos.mediacloud.data.model.UserImage
import com.dynamitos.mediacloud.network.APIClient
import com.dynamitos.mediacloud.network.BASE_URL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


private val RAW_DATA = "[{\"name\":\"Su.jpg\",\"imageURL\":\"img/Dynamitos/Su.jpg\",\"thumbnailURL\":\"img/Dynamitos/thumbnail/Su.jpg\",\"width\":928,\"height\":923},{\"name\":\"Hua.jpg\",\"imageURL\":\"img/Dynamitos/Hua.jpg\",\"thumbnailURL\":\"img/Dynamitos/thumbnail/Hua.jpg\",\"width\":953,\"height\":853},{\"name\":\"Eden.jpg\",\"imageURL\":\"img/Dynamitos/Eden.jpg\",\"thumbnailURL\":\"img/Dynamitos/thumbnail/Eden.jpg\",\"width\":916,\"height\":915},{\"name\":\"Elysia.jpg\",\"imageURL\":\"img/Dynamitos/Elysia.jpg\",\"thumbnailURL\":\"img/Dynamitos/thumbnail/Elysia.jpg\",\"width\":974,\"height\":965},{\"name\":\"Kalpas.jpg\",\"imageURL\":\"img/Dynamitos/Kalpas.jpg\",\"thumbnailURL\":\"img/Dynamitos/thumbnail/Kalpas.jpg\",\"width\":886,\"height\":937},{\"name\":\"Sakura.jpg\",\"imageURL\":\"img/Dynamitos/Sakura.jpg\",\"thumbnailURL\":\"img/Dynamitos/thumbnail/Sakura.jpg\",\"width\":978,\"height\":960},{\"name\":\"Mei.jpg\",\"imageURL\":\"img/Dynamitos/Mei.jpg\",\"thumbnailURL\":\"img/Dynamitos/thumbnail/Mei.jpg\",\"width\":988,\"height\":966},{\"name\":\"Mobius.jpg\",\"imageURL\":\"img/Dynamitos/Mobius.jpg\",\"thumbnailURL\":\"img/Dynamitos/thumbnail/Mobius.jpg\",\"width\":969,\"height\":953},{\"name\":\"Kevin.jpg\",\"imageURL\":\"img/Dynamitos/Kevin.jpg\",\"thumbnailURL\":\"img/Dynamitos/thumbnail/Kevin.jpg\",\"width\":878,\"height\":940}]"
class Util {
    companion object {
        fun getImageData(): List<UserImage> {
            val type = object : TypeToken<List<UserImage>>() {}.type
            return Gson().fromJson<List<UserImage>>(RAW_DATA, type)
        }
    }
}