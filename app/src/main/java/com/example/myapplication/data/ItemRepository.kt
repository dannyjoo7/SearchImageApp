package com.example.myapplication.data

import com.example.myapplication.api.RetrofitInstance
import retrofit2.Response

class ItemRepository :Repository {
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

    suspend fun searchImage(query: String, sort: String): Response<MutableList<Item>> {
        val response = RetrofitInstance.api.searchImage(query = query, sort = sort, page = 1, size = 5)

        if (response.isSuccessful) {
            val imageDTO = response.body()
            val itemList = mutableListOf<Item>()

            // imageDTO에서 필요한 데이터를 추출하고 Item 객체로 변환하여 itemList에 추가
            imageDTO?.documents?.forEach { document ->
                // title null 예외처리
                val title = document.title ?: "(No Title)"
                val item = Item(
                    title = title,
                    display_sitename = document.display_sitename,
                    image_url = document.image_url,
                    datetime = document.datetime
                )
                itemList.add(item)
            }

            return Response.success(itemList)
        } else {
            return Response.error(response.code(), response.errorBody())
        }
    }


}