package com.dynamitos.mediacloud.network

import com.dynamitos.mediacloud.data.LoginDataSource
import com.dynamitos.mediacloud.data.LoginRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "http://192.168.8.26:3000"

class APIClient private constructor() {
    //const val token: String = "271bc5b649181d96cb7d871a3e30eb5cbad7b91e526be9bcefddda6b5dc5b9c56706cf5152143ffcc49753566e6052968e90e4ddb44ff4a8500f1a38b1d6e4e2";
    lateinit var apiService: MediaCloudAPIService
    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    init {
        defaultRetrofit()
    }

    fun defaultRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
        //.client(okhttpClient(token))
            .build()

        apiService = retrofit.create(MediaCloudAPIService::class.java)
    }

    fun tokenRetrofit(token: String){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okhttpClient(token))
            .build()

        apiService = retrofit.create(MediaCloudAPIService::class.java)
    }

    private fun okhttpClient(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()
    }

    companion object {
        @Volatile
        private var instance: APIClient? = null

        fun getInstance(): APIClient {
            return instance ?: synchronized(this) {
                instance ?: APIClient().also { instance = it }
            }
        }
    }
}