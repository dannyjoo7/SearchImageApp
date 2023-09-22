package com.example.myapplication.api

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.ImageResponse
import com.example.myapplication.data.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitApi {
    @GET("v2/search/image")
    suspend fun searchImage(
        @Header("Authorization") apiKey: String = BuildConfig.KAKAO_REST_API_KEY,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
    ): Response<ImageResponse>

    @GET("v2/search/vclip")
    suspend fun searchVideo(
        @Header("Authorization") apiKey: String = BuildConfig.KAKAO_REST_API_KEY,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
    ): Response<VideoResponse>
}