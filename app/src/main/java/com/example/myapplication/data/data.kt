package com.example.myapplication.data

import android.os.Build
import java.time.LocalDateTime


object data {
    val searchData: MutableList<Item> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mutableListOf(
            Item("Item 1", "유튜브", "https://ibb.co/z7PQLnC", LocalDateTime.now()),
            Item("Item 2", "유튜브", "https://ibb.co/z7PQLnC", LocalDateTime.now()),
            Item("Item 3", "유튜브", "https://ibb.co/z7PQLnC", LocalDateTime.now()),
            Item("Item 4", "유튜브", "https://ibb.co/z7PQLnC", LocalDateTime.now()),
            Item("Item 5", "유튜브", "https://ibb.co/z7PQLnC", LocalDateTime.now())
        )
    } else {
        mutableListOf()
    }

    val favoriteData: MutableList<Item> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mutableListOf()
    } else {
        mutableListOf()
    }

    fun removeFavoriteItem(item: Item) {
        favoriteData.remove(item)
    }

    fun addFavoriteItem(item: Item) {
        favoriteData.add(item)
    }
}
