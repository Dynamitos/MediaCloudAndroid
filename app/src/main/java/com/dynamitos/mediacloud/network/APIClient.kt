package com.dynamitos.mediacloud.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://10.0.0.73:3000"

object APIClient {
    private const val token: String = "271bc5b649181d96cb7d871a3e30eb5cbad7b91e526be9bcefddda6b5dc5b9c56706cf5152143ffcc49753566e6052968e90e4ddb44ff4a8500f1a38b1d6e4e2";
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