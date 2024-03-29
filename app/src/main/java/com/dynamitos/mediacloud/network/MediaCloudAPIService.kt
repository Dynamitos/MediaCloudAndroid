package com.dynamitos.mediacloud.network

import com.dynamitos.mediacloud.data.model.UploadImage
import com.dynamitos.mediacloud.data.model.UserAudio
import com.dynamitos.mediacloud.data.model.UserImage
import okhttp3.MultipartBody
import retrofit2.http.*


interface MediaCloudAPIService {
    @FormUrlEncoded
    @POST("/signin")
    suspend fun login(@Field("username") username: String, @Field("password") password: String) : String

    @GET("/img/{user}")
    suspend fun getImages(@Path("user") username: String, @Header("Authentication") token: String): List<UserImage>

    @GET("/music/{user}")
    suspend fun getMusic(@Path("user") username: String, @Header("Authentication") token: String): List<UserAudio>

    @Multipart
    @POST("/img/phash")
    suspend fun uploadPhash(@Part body: MultipartBody.Part)
    @POST("/img/upload")
    suspend fun uploadImages(@Body images: List<UploadImage> )
}
