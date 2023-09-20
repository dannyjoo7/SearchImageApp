package com.example.myapplication.data

import androidx.lifecycle.LiveData

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
}