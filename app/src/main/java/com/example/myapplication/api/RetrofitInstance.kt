package com.example.myapplication.api

import com.example.myapplication.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.KAKAO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : RetrofitApi by lazy {
        retrofit.create(RetrofitApi::class.java)
    }
}