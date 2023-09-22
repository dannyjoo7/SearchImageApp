package com.example.myapplication.data

import android.util.Log
import com.example.myapplication.api.RetrofitInstance
import retrofit2.Response

class ItemRepository : Repository {
    override fun findSearchItems(): MutableList<Item> {
        return Data.getSearchData()
    }

    override fun findFavoriteItems(): MutableList<Item> {
        return Data.getFavoriteData()
    }

    override fun removeFavoriteItem(item: Item) {
        Data.removeFavoriteItem(item)
    }

    override fun addFavoriteItem(item: Item) {
        Data.addFavoriteItem(item)
    }

    suspend fun searchImage(
        query: String,
        sort: String,
    ): Response<MutableList<Item>> {
        val response =
            RetrofitInstance.api.searchImage(query = query, sort = sort, page = 1, size = 80)

        if (response.isSuccessful) {
            val imageResponse = response.body()
            val imgList = mutableListOf<Item>()

            imageResponse?.documents?.forEach { document ->
                // title null 예외처리
                val title = document.title ?: "(No Title)"
                val item = Item(
                    title = title,
                    display_sitename = document.display_sitename,
                    image_url = document.image_url,
                    datetime = document.datetime,
                    isFavorite = false
                )
                imgList.add(item)
            }
            return Response.success(imgList)
        } else {
            return Response.error(response.code(), response.errorBody())
        }
    }

    suspend fun searchVideo(
        query: String,
        sort: String,
    ): Response<MutableList<Item>> {
        val response =
            RetrofitInstance.api.searchVideo(query = query, sort = sort, page = 1)

        if (response.isSuccessful) {
            val videoResponse = response.body()
            val videoList = mutableListOf<Item>()

            videoResponse?.documents?.forEach { document ->
                // title null 예외처리
                val title = document.title ?: "(No Title)"
                val item = VideoItem(
                    title = title,
                    url = document.url,
                    datetime = document.datetime,
                    thumbnail = document.thumbnail,
                    isFavorite = false
                )
                videoList.add(item.toItem())
            }

            return Response.success(videoList)
        } else {
            return Response.error(response.code(), response.errorBody())
        }
    }


}