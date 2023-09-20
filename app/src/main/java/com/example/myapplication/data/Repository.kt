package com.example.myapplication.data

import androidx.lifecycle.LiveData

interface Repository {
    fun findSearchItems(): MutableList<Item>
    fun findFavoriteItems(): MutableList<Item>
    fun removeFavoriteItem(item: Item)
    fun addFavoriteItem(item: Item)
}