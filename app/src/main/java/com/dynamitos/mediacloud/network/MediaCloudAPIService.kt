package com.dynamitos.mediacloud.network

import com.dynamitos.mediacloud.data.model.UserImageList
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://10.0.0.73:3000"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MediaCloudAPIService {
    @FormUrlEncoded
    @POST("/signin")
    suspend fun login(@Field("username") username: String, @Field("password") password: String) : String

    @GET("/img/{user}")
    suspend fun getImages(@Path("user") username: String): UserImageList
}

object MediaCloudAPI {
    val apiService : MediaCloudAPIService by lazy {
        retrofit.create(MediaCloudAPIService::class.java)
    }
}