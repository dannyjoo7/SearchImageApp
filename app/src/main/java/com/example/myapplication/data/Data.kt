package com.example.myapplication.data

import android.os.Build
import java.util.Date


object Data {
    private val searchData: MutableList<Item> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mutableListOf()
        } else {
            mutableListOf()
        }

    private val favoriteData: MutableList<Item> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mutableListOf(
                Item(id=-1, title="(No Title)", display_sitename="티스토리", image_url="https://blog.kakaocdn.net/dn/deu5VO/btsu0t56Rwg/2eFeBJxsDbVBomId0R9sk1/img.jpg", datetime=Date(), isFavorite=true)
            )
        } else {
            mutableListOf()
        }

    fun getSearchData(): MutableList<Item> {
        return searchData
    }

    fun getFavoriteData(): MutableList<Item> {
        return favoriteData
    }

    fun removeFavoriteItem(item: Item) {
        favoriteData.remove(item)
    }

    fun addFavoriteItem(item: Item) {
        favoriteData.add(item)
    }
}
