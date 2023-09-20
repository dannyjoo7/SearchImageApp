package com.example.myapplication.data

import android.os.Build
import java.util.Date


object Data {
    private val searchData: MutableList<Item> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mutableListOf(
                Item(0, "Item 1", "유튜브", "https://ibb.co/z7PQLnC", Date()),
                Item(1, "Item 2", "유튜브", "https://ibb.co/z7PQLnC", Date()),
                Item(2, "Item 3", "유튜브", "https://ibb.co/z7PQLnC", Date()),
                Item(3, "Item 4", "유튜브", "https://ibb.co/z7PQLnC", Date()),
                Item(4, "Item 5", "유튜브", "https://ibb.co/z7PQLnC", Date())
            )
        } else {
            mutableListOf()
        }

    private val favoriteData: MutableList<Item> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mutableListOf()
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
