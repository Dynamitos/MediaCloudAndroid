package com.dynamitos.mediacloud.network

import com.dynamitos.mediacloud.data.model.UserImage
import retrofit2.http.*


interface MediaCloudAPIService {
    @FormUrlEncoded
    @POST("/signin")
    suspend fun login(@Field("username") username: String, @Field("password") password: String) : String

    @GET("/img/{user}")
    suspend fun getImages(@Path("user") username: String, @Header("Authentication") token: String): List<UserImage>
}
