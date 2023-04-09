package com.dynamitos.mediacloud.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://10.0.0.73:3000"

class APIClient(token: String) {
    private val apiService: MediaCloudAPIService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient(token))
            .build()

        apiService = retrofit.create(MediaCloudAPIService::class.java)
    }

    private fun okhttpClient(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()
    }
}