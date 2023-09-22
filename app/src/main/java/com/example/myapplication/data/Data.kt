package com.example.myapplication.data

import android.os.Build
import java.util.Date


object Data {
    private val searchData: MutableList<Item> = mutableListOf()
    private val favoriteData: MutableList<Item> = mutableListOf()

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
